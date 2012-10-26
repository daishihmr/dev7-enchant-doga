enchant();
window.onload = function() {
	var game = new Game();
	game.preload("robo17.l3c.js")
	game.onload = function() {
		var scene = new Scene3D();
		var m = game.assets["robo17.l3c.js"];
		m.scaleX = m.scaleY = m.scaleZ = 0.6;
		scene.addChild(m);

		var area = document.getElementById("poses");
		for ( var k in m.poses) {
			if (m.poses.hasOwnProperty(k)) {
				var btn = document.createElement("button");
				btn.appendChild(document.createTextNode(k));
				btn.value = k;
				area.appendChild(btn);
				btn.onclick = function() {
					m.animate(this.value, 20);
				};
			}
		}
	};
	game.start();
};
