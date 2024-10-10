export function a() {
  if (import.meta.env.VITE_DEV === 'true'){
    return;
  }
  document.addEventListener('contextmenu', function (b) {
    b.preventDefault();
  });

  document.addEventListener('keydown', function (b) {
    if (
      b.key === 'F12' ||
      (b.ctrlKey && b.shiftKey && (b.key === 'I' || b.key === 'J' || b.key === 'C')) ||
      (b.ctrlKey && b.key === 'U')
    ) {
      b.preventDefault();
    }
  });

  function c() {
    const d = 160;
    let e = false;

    const f = () => {
      const g = window.outerWidth - window.innerWidth > d;
      const h = window.outerHeight - window.innerHeight > d;

      if (g || h) {
        if (!e) {
          e = true;
          i();
        }
      } else {
        e = false;
      }
    };

    const i = () => {
      function j() {
        debugger;
        setTimeout(j, 100);
      }
      j();
    };

    setInterval(f, 500);
  }

  c();
}
