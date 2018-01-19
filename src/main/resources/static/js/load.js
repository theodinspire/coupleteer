document.addEventListener('DOMContentLoaded', function () {
  pullCouplet();
});

function pullCouplet() {
  var coupletLine1, coupletLine2;

  coupletLine1 = document.getElementById('couplet');
  coupletLine2 = coupletLine1.children[1];
  coupletLine1 = coupletLine1.children[0];

  fetch('/api/couplet').then(function (res) {
    res.json().then(function (data) {
      document.body.className = null;
      coupletLine1.textContent = data.first;
      coupletLine2.textContent = data.second;
    });
  });
}
