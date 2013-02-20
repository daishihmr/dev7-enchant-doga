enchant();
window.onload = function() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (this.readyState == this.DONE) {
            if (this.status == 200) {
                start(JSON.parse(this.responseText));
            }
        }
    };
    xhr.open("GET", "/modelList");
    xhr.send();
};

var model;
var start = function(modelList) {
    var game = new Game();
    game.keybind("Z".charCodeAt(0), "a");
    game.keybind("X".charCodeAt(0), "b");
    game.keybind("C".charCodeAt(0), "c");
    game.onload = function() {
        var scene = new Scene3D();
        scene.backgroundColor = [1,1,1,0];

        var cam = scene.getCamera();

        var distance = 5;
        var center = new Sprite3D();
        var camY = 0;
        scene.addChild(center);
        center.onenterframe = function() {
            cam.lookAt(this);
            cam.chase(this, distance, 5);
            cam.y = camY;
        };

        game.onenterframe = function() {
            if (game.input.c) {
                if (game.input.up) {
                    center.altitude(-0.05);
                } else if (game.input.down) {
                    center.altitude(+0.05);
                }
                if (game.input.left) {
                    center.sidestep( 0.05);
                } else if (game.input.right) {
                    center.sidestep(-0.05)
                }
            } else {
                if (game.input.up) {
                    camY += 0.1;
                } else if (game.input.down) {
                    camY -= 0.1;
                }
                if (game.input.left) {
                    center.rotateYaw(+0.1);
                } else if (game.input.right) {
                    center.rotateYaw(-0.1);
                }
                if (game.input.a) {
                    distance -= 0.1;
                } else if (game.input.b) {
                    distance += 0.1;
                }
            }
        };
    };
    game.start();
    game.loadModel = function(modelName) {
        game.load(modelName, function() {
            var scene = game.currentScene3D;
            if (scene.currentModel) {
                scene.removeChild(scene.currentModel);
            }

            model = scene.currentModel = game.assets[modelName];
            scene.addChild(model);

            var poseList = document.getElementById("pose");
            var items = [];
            for (var i = 0, end = poseList.childNodes.length; i < end; i++) {
                items[i] = poseList.childNodes.item(i);
            }
            items.forEach(function(item) {
                poseList.removeChild(item);
            });

            if (model.poses) {
                var poses = [];
                for (var name in model.poses) {
                    poses[poses.length] = name;
                }
                poses.sort();
                poses.forEach(function(pose) {
                    var item = document.createElement("option");
                    item.textContent = pose;
                    poseList.appendChild(item);
                });
                poseList.onchange = function() {
                    var p = poses[this.selectedIndex];
                    if (p) {
                        model.tl.motion(p, 30);
                    }
                };
                poseList.style.display = "inline-block";
            } else {
                poseList.style.display = "none";
            }
        });
    };

    var menu = document.getElementById("menu");
    for (var i = 0, end = modelList.length; i < end; i++) {
        var item = document.createElement("option");
        item.textContent = modelList[i];
        menu.appendChild(item);
    }
    menu.onchange = function() {
        var modelName = modelList[this.selectedIndex];
        if (modelName) {
            game.loadModel("/" + modelName);
        }
    };
    menu.onchange();
};
