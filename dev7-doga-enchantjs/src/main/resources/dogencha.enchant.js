/** ロード処理. */
enchant.Game._loadFuncs["js"] = function(src, callback, ext) {
	var g = this;
	if (callback == null) {
		callback = function() {
		};
	}

	function endsWith(string, value) {
		return string.indexOf(value) === string.length - value.length;
	}
	function each(obj, func) {
		for ( var k in obj) {
			if (obj.hasOwnProperty(k)) {
				func(k, obj[k]);
			}
		}
	}
	function ajax(src, build) {
		var req = new XMLHttpRequest();
		req.open('GET', src, true);
		req.onreadystatechange = function(e) {
			if (req.readyState == 4) {
				if (req.status != 200 && req.status != 0) {
					throw new Error(req.status + ': '
							+ 'Cannot load an asset: ' + src);
				}
				g.assets[src] = build(req.responseText);
				callback();
			}
		};
		req.send(null);
	}

	function buildL3p(obj) {
		var root = new Sprite3D();
		each(obj, function(atrName, data) {
			var part = new Sprite3D();
			var mesh = new Mesh();
			mesh.vertices = data.vertices;
			mesh.normals = data.normals;
			mesh.texCoords = data.texCoords;
			mesh.indices = data.indices;
			mesh.setBaseColor("#ffffffff");

			var tex = new Texture();
			tex.ambient = data.texture.ambient;
			tex.diffuse = data.texture.diffuse;
			tex.specular = data.texture.specular;
			tex.emission = data.texture.emission;
			tex.shininess = data.texture.shininess;
			// TODO いつか対応する
			// if (data.texture.src) {
			// tex.src = data.texture.src;
			// }
			mesh.texture = tex;
			part.mesh = mesh;

			part.name = data.name;

			root.addChild(part);
		});
		return root;
	}

	function buildL3c(data) {
		function _tree(_unit) {
			var _result = {};
			_result.node = buildL3p(_unit.l3p);
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
			each(poseNode.childUnits, function(i, v) {
				v.parentNode = poseNode;
				_setupPoseNode(v);
			});
		}

		var result = enchant.gl.dogencha.Unit.build(_tree(data.root));

		result.poses = {};
		each(data.poses, function(k, v) {
			result.poses[v.name] = v.root;
			_setupPoseNode(result.poses[v.name]);
		});

		if (result.poses._initialPose) {
			result.setPose(result.poses._initialPose);
		}
		return result;
	}

	if (endsWith(src, ".l3p.js")) {
		console.info("request l3p [" + src + "]");
		ajax(src, function(text) {
			console.info("load l3p [" + src + "] ok");
			try {
				var root = buildL3p(JSON.parse(text));
				console.info("parse l3p [" + src + "] ok");
				return root;
			} catch (e) {
				console.error("l3p [" + src + "] のビルド中にエラー");
				throw e;
			}
		});
	} else if (endsWith(src, ".l3c.js")) {
		console.info("request l3c [" + src + "]");
		ajax(src, function(text) {
			console.info("load l3c [" + src + "] ok");
			try {
				var result = buildL3c(JSON.parse(text));
				console.info("parse l3c [" + src + "] ok");
				return result;
			} catch (e) {
				console.error("l3c [" + src + "] のビルド中にエラー");
				throw e;
			}
		});
	} else {
		console.debug("request js [" + src + "]");
		ajax(src, function(text) {
			console.info("load js [" + src + "] ok");
			return text;
		});
	}
};

enchant.gl.dogencha = {};

/**
 * DoGA L3Cモデルのユニット.
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

		/**
		 * アニメーション中に毎フレーム実行される関数.
		 */
		this.tickListener = null;

		/** ポーズデータ.ルートオブジェクトのみが持つ. */
		this.poses = null;

		/** モーション中. */
		this._inMotion = false;

		this.addChild(entity);
		this.addEventListener("enterframe", (function() {
			var listener = function() {
				if (this.tickListener) {
					this.tickListener();
				}
			};
			listener.animation = true;
			return listener;
		})());
	},
	entity : {
		get : function() {
			return this._entity;
		}
	},
	inMotion : {
		get : function() {
			return this._inMotion;
		}
	},
	/**
	 * 子ユニットを追加する.
	 */
	addChildUnit : function(childUnit) {
		this.addChild(childUnit);
		this.childUnits[this.childUnits.length] = childUnit;
	},
	animate : function(pose, frameNum, callback) {
		if (typeof (pose) == "string") {
			pose = this.poses[pose];
		}
		if (!pose) {
			pose = this.poses._initialPose;
		}
		this._inMotion = true;
		var startFrame = this.age;
		var endFrame = this.age + frameNum;

		var from = this.getPose();

		this.tickListener = function(e) {
			// この内部のthisはUnitを指す
			this.setFrame(from, pose, (this.age - startFrame) / frameNum);
			if (this.age >= endFrame) {
				this._inMotion = false;
				this.setPose(pose);
				this.tickListener = null;
				if (callback) {
					callback.apply(this);
				}
			}
		};
	},
	wait : function(frameNum, callback) {
		this.animate(this.getPose(), frameNum, callback);
	},
	cancelAnimation : function() {
		this.tickListener = null;
	},
	setFrame : function(from, to, ratio) {
		if (!from) {
			from = this.poses._initialPose;
		}
		if (!to) {
			to = this.poses._initialPose;
		}
		function _setFrameToUnit(node, fromNode, toNode, ratio) {
			if (!fromNode) {
				fromNode = {};
				fromNode.pose = [ 0, 0, 0, 0, 0, 0 ];
				fromNode.quat = new Quat(0, 0, 0, 0);
				fromNode.childUnits = [];
			}
			if (!toNode) {
				toNode = {};
				toNode.pose = [ 0, 0, 0, 0, 0, 0 ];
				toNode.quat = new Quat(0, 0, 0, 0);
				toNode.childUnits = [];
			}

			var nodeQuat = new Quat(0, 0, 0, 0);
			nodeQuat._quat = quat4.create(fromNode.quat._quat);

			var toQ = new Quat(0, 0, 0, 0);
			toQ._quat = quat4.create(toNode.quat._quat);

			(function(quat, another, ratio) {
				var ac = another.clone();
				if (quat4.dot(quat._quat, ac._quat) < 0) {
					quat4.reverse(ac._quat);
				}
				quat4.slerp(quat._quat, ac._quat, ratio);
				return quat;
			})(nodeQuat, toQ, ratio);

			node.quat = nodeQuat;
			node.rotationSet(nodeQuat);

			node.x = fromNode.pose[3] + (toNode.pose[3] - fromNode.pose[3])
					* ratio + node.baseX;
			node.y = fromNode.pose[4] + (toNode.pose[4] - fromNode.pose[4])
					* ratio + node.baseY;
			node.z = fromNode.pose[5] + (toNode.pose[5] - fromNode.pose[5])
					* ratio + node.baseZ;

			node.childUnits.forEach(function(v, i) {
				_setFrameToUnit(node.childUnits[i], fromNode.childUnits[i],
						toNode.childUnits[i], ratio);
			});
		}
		_setFrameToUnit(this, from, to, ratio);
	},
	setPose : function(pose) {
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
		_setPoseToUnit(this, pose);
	},
	getPose : function() {
		function _getPose(node, parent) {
			var p = {};
			p.pose = [ 0, 0, 0, node.x - node.baseX, node.y - node.baseY,
					node.z - node.baseZ ];
			p.quat = node.quat;
			p.parentNode = parent;
			p.childUnits = [];
			node.childUnits.forEach(function(v, i) {
				p.childUnits[i] = _getPose(node.childUnits[i], p);
			});
			return p;
		}

		return _getPose(this, null);
	},
	clone : function() {
		var clone = new enchant.gl.dogencha.Unit(this._entity.clone(), [ this.baseX, this.baseY,
				this.baseZ ]);
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

quat4.dot = function(quat, quat2) {
	return quat[0] * quat2[0] + quat[1] * quat2[1] + quat[2] * quat2[2]
			+ quat[3] * quat2[3];
};
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
enchant.gl.Quat.prototype.clone = function() {
	var clone = new Quat(0, 0, 0, 0);
	clone._quat[0] = this._quat[0];
	clone._quat[1] = this._quat[1];
	clone._quat[2] = this._quat[2];
	clone._quat[3] = this._quat[3];
	return clone;
};
