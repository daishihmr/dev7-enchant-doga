enchant();
window.onload = function() {
    var FSC = "test.fsc.js";
    var L2P = "test.l2p.js";
    var L3P = "test.l3p.js";

    var game = new Game();
    game.preload(FSC, L2P, L3P);
    game.onload = function() {
        var scene = new Scene3D();
        game.assets[FSC].x = -1;
        game.assets[L2P].x = 0;
        game.assets[L3P].x = 1;
        scene.addChild(game.assets[FSC]);
        scene.addChild(game.assets[L2P]);
        scene.addChild(game.assets[L3P]);

        var p = 0;

        game.on("enterframe", function() {
            var cam = scene.getCamera();
            cam.x = Math.cos(p) * 5;
            cam.z = Math.sin(p) * 5;

            if (game.input.left) p -= 0.1;
            else if (game.input.right) p += 0.1;
            if (game.input.down) cam.y -= 0.1;
            else if (game.input.up) cam.y += 0.1;
        });
    };
    game.start();
};
