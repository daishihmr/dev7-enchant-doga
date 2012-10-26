enchant();
window.onload = function() {
	var game = new Game();
	game.preload("/data.l3c.js")
	game.onload = function() {
		var scene = new Scene3D();
		scene.backgroundColor = [ 1, 1, 1, 1 ];
		var m = (function() {
			var m = new Sprite3D();
			m.addChild(game.assets["/data.l3c.js"]);
			return m;
		})();
		m.scaleX = m.scaleY = m.scaleZ = 2.6;
		m.y = -1;
		scene.addChild(m);
		game.addEventListener("enterframe", function() {
			if (game.input.left) {
				m.rotateYaw(-0.1);
			} else if (game.input.right) {
				m.rotateYaw(0.1);
			}
		});

		var area = document.getElementById("poses");
		for ( var k in m.childNodes[0].poses) {
			if (m.childNodes[0].poses.hasOwnProperty(k)) {
				var btn = document.createElement("button");
				btn.appendChild(document.createTextNode(k));
				btn.value = k;
				area.appendChild(btn);
				btn.onclick = function() {
					m.childNodes[0].animate(this.value, 20);
				};
			}
		}
	};
	game.start();
};
