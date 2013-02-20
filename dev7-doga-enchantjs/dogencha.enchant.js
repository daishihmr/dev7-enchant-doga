/**
 * dogencha.enchant.js
 * version 0.5-RC1
 */
(function() {

    /**
     * @namespace
     */
    enchant.gl.dogencha = {};
    enchant.gl.dogencha.DOMAIN = "doga.dev7.jp";
    // enchant.gl.dogencha.DOMAIN = "localhost:9000";

    /**
     * ajaxで取得したユニットオブジェクトを元に、Sprite3Dを作成する.
     */
    function buildUnit(geometries, textures) {
        textures = textures || {};

        var root = new Sprite3D();
        for (var atrName in geometries) {
            var geom = geometries[atrName];
            var part = new Sprite3D();
            var mesh = new Mesh();
            mesh.vertices = geom.vertices;
            mesh.normals = geom.normals;
            mesh.texCoords = geom.texCoords;
            mesh.indices = geom.indices;
            mesh.setBaseColor("#ffffffff");

            var tex = new Texture();
            tex.ambient = geom.texture.ambient;
            tex.diffuse = geom.texture.diffuse;
            tex.specular = geom.texture.specular;
            tex.emission = geom.texture.emission;
            tex.shininess = geom.texture.shininess;
            if (geom.texture.src && textures[geom.texture.src]) {
                tex.src = textures[geom.texture.src];
            }
            mesh.texture = tex;
            part.mesh = mesh;

            part.name = geom.name;

            root.addChild(part);
        }
        return root;
    }

    /**
     * ajaxで取得した多関節物体オブジェクトを元に、Sprite3Dを作成する.
     */
    function buildArticulated(geometries, textures) {
        function _tree(_unit) {
            var _result = {};
            _result.node = buildUnit(_unit.l3p, textures);
            _result.basePosition = _unit.basePosition;
            if (_unit.childUnits instanceof Array) {
                _result.child = _unit.childUnits.map(function(c) {
                    return _tree(c);
                });
            }
            return _result;
        }
        function _setupPoseNode(poseNode) {
            poseNode.quat = (function() {
                var newQuat = new Quat(0, 0, 0, 0);
                newQuat._quat = quat4.create(poseNode.quat);
                return newQuat;
            })();
            for (var i = 0, end = poseNode.childUnits.length; i < end; i++) {
                var child = poseNode.childUnits[i];
                child.parentNode = poseNode;
                _setupPoseNode(child);
            }
        }

        var rootUnit = enchant.gl.dogencha.Unit.build(_tree(geometries.root));
        var result = new enchant.gl.dogencha.Articulated(rootUnit);

        result.poses = {};
        for (var i = 0, end = geometries.poses.length; i < end; i++) {
            var pose = geometries.poses[i];
            result.poses[pose.name] = pose.root;
            _setupPoseNode(result.poses[pose.name]);
        }

        if (result.poses._initialPose) {
            result.setPose(result.poses._initialPose);
        }
        return result;
    }

    var loadFunc = function(game, src, callback, ext, ajaxFunc) {
        calback = callback || function() {
        };

        var endsWith = function(string, value) {
            return new RegExp(value + "$").test(string);
        }

        if (endsWith(src, ".fsc.js")    || endsWith(src, ".l2p.js")    || endsWith(src, ".l3p.js") ||
            endsWith(src, ".fsc.json")  || endsWith(src, ".l2p.json")  || endsWith(src, ".l3p.json") ||
            endsWith(src, ".fsc.jsonp") || endsWith(src, ".l2p.jsonp") || endsWith(src, ".l3p.jsonp")) {
            console.info("request unit [" + src + "]");
            ajaxFunc(src, function(data) {
                // console.debug("load unit [" + src + "] ok");
                try {
                    var root = buildUnit(data.geometries, data.textures);
                    console.info("parse unit [" + src + "] ok");
                    game.assets[src] = root;
                } catch (e) {
                    console.error("unit [" + src + "] のビルド中にエラー");
                    throw e;
                }
                callback();
            });
        } else if (endsWith(src, ".l2c.js") || endsWith(src, ".l2c.json") || endsWith(src, ".l2c.jsonp") ||
                   endsWith(src, ".l3c.js") || endsWith(src, ".l3c.json") || endsWith(src, ".l3c.jsonp")) {
            console.info("request l3c [" + src + "]");
            ajaxFunc(src, function(data) {
                // console.debug("load l3c [" + src + "] ok");
                try {
                    var result = buildArticulated(data.geometries, data.textures);
                    console.info("parse l3c [" + src + "] ok");
                    game.assets[src] = result;
                } catch (e) {
                    console.error("l3c [" + src + "] のビルド中にエラー");
                    throw e;
                }
                callback();
            });
        } else {
            console.info("request js [" + src + "]");
            ajaxFunc(src, function(data) {
                // console.debug("load js [" + src + "] ok");
                game.assets[src] = data;
                callback();
            });
        }
    };

    var origJsonpLoadFunc = enchant.Game._loadFuncs["jsonp"];
    enchant.Game._loadFuncs["jsonp"] = function(src, callback, ext) {
        if (src.indexOf(enchant.gl.dogencha.DOMAIN) != -1) {
            loadFunc(this, src, callback, ext, getJsonp);
        } else {
            origJsonpLoadFunc.apply(this, arguments);
        }
    };
    enchant.Game._loadFuncs["js"] = enchant.Game._loadFuncs["json"] = function(src, callback, ext) {
        loadFunc(this, src, callback, ext, getJson);
    };

    /**
     * DOGA多関節物体.
     */
    enchant.gl.dogencha.Articulated = enchant.Class.create(enchant.gl.Sprite3D, {
        initialize: function(unit) {
            enchant.gl.Sprite3D.call(this);
            this.root = unit.clone();
            this.addChild(this.root);

            this.poses = this.root.poses;

            this.animationFrame = 1.0;
            this.beforePose = this.nextPose = null;
            this.on("enterframe", function() {
                if (this.animationFrame === 1.0 || this.beforePose === this.nextPose) {
                    return;
                }
                this.setFrame(this.beforePose, this.nextPose, this.animationFrame);
            });

            this.tl = this.tl || new enchant.Timeline(this);
            var self = this;
            this.tl.motion = function(pose, time, easing) {
                if (typeof (pose) === "string") {
                    pose = self.poses[pose];
                }
                if (!pose) {
                    pose = self.poses._initialPose;
                }

                return this.then(function() {
                    self.beforePose = self.getCurrentPose();
                    self.nextPose = pose;
                    self.animationFrame = 0.0;
                }).tween({
                    animationFrame: 1.0,
                    time: time,
                    easing: easing
                });
            };
        },
        getCurrentPose : function() {
            function _currentPose(node, parent) {
                var p = {};
                p.pose = [ 0, 0, 0, node.x - node.baseX, node.y - node.baseY, node.z - node.baseZ ];
                p.quat = node.quat;
                p.parentNode = parent;
                p.childUnits = [];
                for (var i = 0, end = node.childUnits.length; i < end; i++) {
                    p.childUnits[i] = _currentPose(node.childUnits[i], p);
                }
                return p;
            }

            return _currentPose(this.root, null);
        },
        setPose : function(pose) {
            if (typeof (pose) === "string") {
                pose = this.poses[pose];
            }
            if (!pose) {
                pose = this.poses._initialPose;
            }

            function _setPoseToUnit(node, p) {
                if (!p) {
                    p = {};
                    p.pose = [ 0, 0, 0, 0, 0, 0 ];
                    p.childUnits = [];
                    p.quat = new Quat(0, 0, 0, 0);
                }

                node.quat = p.quat;
                node.rotationSet(p.quat);

                node.x = p.pose[3] + node.baseX;
                node.y = p.pose[4] + node.baseY;
                node.z = p.pose[5] + node.baseZ;

                node.childUnits.forEach(function(child, i) {
                    _setPoseToUnit(child, p.childUnits[i]);
                });
            }
            _setPoseToUnit(this.root, pose);

            this.animationFrame = 1.0;
            this.beforePose = this.nextPose = pose;
        },
        setFrame : function(beforePose, nextPose, ratio) {
            if (!beforePose) {
                beforePose = this.poses._initialPose;
            }
            if (!nextPose) {
                nextPose = this.poses._initialPose;
            }
            function _setFrameToUnit(unit, before, next, ratio) {
                if (!before) {
                    before = {};
                    before.pose = [ 0, 0, 0, 0, 0, 0 ];
                    before.quat = new Quat(0, 0, 0, 0);
                    before.childUnits = [];
                }
                if (!next) {
                    next = {};
                    next.pose = [ 0, 0, 0, 0, 0, 0 ];
                    next.quat = new Quat(0, 0, 0, 0);
                    next.childUnits = [];
                }

                var unitQuat = new Quat(0, 0, 0, 0);
                unitQuat._quat = quat4.create(before.quat._quat);

                var toQ = new Quat(0, 0, 0, 0);
                toQ._quat = quat4.create(next.quat._quat);

                (function(quat, another, ratio) {
                    var ac = another.clone();
                    if (quat4.dot(quat._quat, ac._quat) < 0) {
                        quat4.reverse(ac._quat);
                    }
                    quat4.slerp(quat._quat, ac._quat, ratio);
                    return quat;
                })(unitQuat, toQ, ratio);

                unit.quat = unitQuat;
                unit.rotationSet(unitQuat);

                unit.x = before.pose[3] + (next.pose[3] - before.pose[3]) * ratio + unit.baseX;
                unit.y = before.pose[4] + (next.pose[4] - before.pose[4]) * ratio + unit.baseY;
                unit.z = before.pose[5] + (next.pose[5] - before.pose[5]) * ratio + unit.baseZ;

                for (var i = 0, end = unit.childUnits.length; i < end; i++) {
                    _setFrameToUnit(unit.childUnits[i], before.childUnits[i], next.childUnits[i], ratio);
                }
            }
            _setFrameToUnit(this.root, beforePose, nextPose, ratio);
        },
        clone: function() {
            return new enchant.gl.dogencha.Articulated(this.root.clone());
        }
    });

    /**
     * DOGA多関節物体の部品.
     */
    enchant.gl.dogencha.Unit = enchant.Class.create(enchant.gl.Sprite3D, {
        initialize : function(entity, basePosition) {
            enchant.gl.Sprite3D.call(this);

            /**
             * データの実体
             *
             * @type {Sprite3D}
             */
            this._entity = entity;

            /**
             * ユニットの基本位置.
             */
            this.baseX = basePosition[0];
            this.baseY = basePosition[1];
            this.baseZ = basePosition[2];

            /**
             * ユニットツリーの子ノード.
             *
             * enchant.jsのイベントツリーとは関係ない.
             */
            this.childUnits = [];

            /** ポーズデータ.ルートオブジェクトのみが持つ. */
            this.poses = null;

            this.addChild(entity);
        },
        entity : {
            get : function() {
                return this._entity;
            }
        },
        /**
         * 子ユニットを追加する.
         */
        addChildUnit : function(childUnit) {
            this.addChild(childUnit);
            this.childUnits[this.childUnits.length] = childUnit;
        },
        clone : function() {
            var clone = new enchant.gl.dogencha.Unit(this._entity.clone(), [
                    this.baseX, this.baseY, this.baseZ ]);
            for ( var i = 0, end = this.childUnits.length; i < end; i++) {
                clone.addChildUnit(this.childUnits[i].clone());
            }
            if (this.poses) {
                clone.poses = this.poses;
                clone.setPose(clone.poses._initialPose);
            }
            return clone;
        }
    });

    enchant.gl.dogencha.Unit.prototype.quat = new enchant.gl.Quat(0, 0, 0, 0);
    enchant.gl.dogencha.Unit.prototype.baseX = 0;
    enchant.gl.dogencha.Unit.prototype.baseY = 0;
    enchant.gl.dogencha.Unit.prototype.baseZ = 0;
    enchant.gl.dogencha.Unit.build = function(arg) {
        var node = new enchant.gl.dogencha.Unit(arg.node, arg.basePosition);
        var child = arg.child;
        if (child instanceof Array) {
            for ( var i = 0, end = child.length; i < end; i++) {
                node.addChildUnit(enchant.gl.dogencha.Unit.build(child[i]));
            }
        }
        return node;
    };

    /** クォータニオンの内積. */
    quat4.dot = function(quat, quat2) {
        return quat[0] * quat2[0] + quat[1] * quat2[1] + quat[2] * quat2[2] + quat[3] * quat2[3];
    };

    /** 逆回転クォータニオン */
    quat4.reverse = function(quat, dest) {
        if (!dest || quat == dest) {
            quat[0] *= -1;
            quat[1] *= -1;
            quat[2] *= -1;
            quat[3] *= -1;
            return quat;
        }
        dest[0] = -quat[0];
        dest[1] = -quat[1];
        dest[2] = -quat[2];
        dest[3] = -quat[3];
        return dest;
    };

    /**
     * 複製する.
     *
     * @scope enchant.gl.Quat.prototype
     */
    enchant.gl.Quat.prototype.clone = function() {
        var clone = new Quat(0, 0, 0, 0);
        clone._quat[0] = this._quat[0];
        clone._quat[1] = this._quat[1];
        clone._quat[2] = this._quat[2];
        clone._quat[3] = this._quat[3];
        return clone;
    };

    function getJson(src, success) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', src, true);
        xhr.onreadystatechange = function(e) {
            if (xhr.readyState === 4) {
                if (xhr.status !== 200 && xhr.status !== 0) {
                    throw new Error(xhr.status + ': ' + 'Cannot load an asset: ' + src);
                }
                success(JSON.parse(xhr.responseText));
            }
        };
        xhr.send(null);
    }
    function getJsonp(url, success) {
        var callbackName = "dogencha" + ("" + Math.random()).replace(/\D/g, "") + "_" + new Date().getTime();
        var elm = document.createElement("script");
        elm.src = url + "?callback=" + callbackName;
        document.body.appendChild(elm);
        window[callbackName] = function() {
            try {
                success.apply(null, arguments);
            } finally {
                document.body.removeChild(elm);
            }
        };
    }

})();
