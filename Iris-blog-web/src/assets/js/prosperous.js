var a_idx = 0;

document.addEventListener('DOMContentLoaded', function () {
	document.body.addEventListener('click', function (e) {
		var a = ['富强', '民主', '文明', '和谐', '自由', '平等', '公正', '法治', '爱国', '敬业', '诚信', '友善'];
		var span = document.createElement('span');
		span.textContent = a[a_idx];
		a_idx = (a_idx + 1) % a.length;
		var x = e.pageX, y = e.pageY;
		let scrolly = window.pageYOffset || document.body.scrollTop || document.documentElement.scrollTop;
		y = y - scrolly;

		span.style.zIndex = 999;
		span.style.top = (y - 20) + 'px';
		span.style.left = x + 'px';
		span.style.position = 'fixed';
		span.style.fontWeight = 'bold';
		span.style.color = 'rgb(' + Math.floor(255 * Math.random()) + ',' + Math.floor(255 * Math.random()) + ',' + Math.floor(255 * Math.random()) + ')';

		document.body.appendChild(span);

		setTimeout(function () {
			span.style.transition = 'top 1.5s ease, opacity 1.5s ease';
			span.style.top = (y - 180) + 'px';
			span.style.opacity = 0;

			setTimeout(function () {
				document.body.removeChild(span);
			}, 1500);
		}, 0);
	});
});
