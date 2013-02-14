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

var start = function(modelList) {
    var game = new Game();
    game.keybind("Z".charCodeAt(0), "a");
    game.keybind("X".charCodeAt(0), "b");
    game.keybind("C".charCodeAt(0), "c");
    var scene;
    game.onload = function() {
        scene = new Scene3D();
        scene.backgroundColor = [1,1,1,0];

        var cam = scene.getCamera();

        var distance = 5;
        var center = new Sprite3D();
        scene.addChild(center);
        center.onenterframe = function() {
            cam.lookAt(this);
            cam.chase(this, distance, 5);
        };

        game.onenterframe = function() {
            if (game.input.c) {
                if (game.input.up) {
                    center.y += 0.1;
                } else if (game.input.down) {
                    center.y -= 0.1;
                }
                if (game.input.left) {
                    center.x -= 0.1;
                } else if (game.input.right) {
                    center.x += 0.1;
                }
            } else {
                if (game.input.up) {
                    center.rotatePitch(+0.1);
                } else if (game.input.down) {
                    center.rotatePitch(-0.1);
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
    game.loadModel = function(modelName) {
        game.load(modelName, function() {
            if (scene.currentModel) {
                scene.removeChild(scene.currentModel);
            }

            scene.currentModel = game.assets[modelName];
            scene.addChild(scene.currentModel);
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

    game.start();
};
