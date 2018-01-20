document.addEventListener('DOMContentLoaded', function () {
  pullCouplet();
});

function pullCouplet() {
  var coupletLine1, coupletLine2, refreshButton;

  document.body.className = 'loading';

  coupletLine1 = document.getElementById('line-one');
  coupletLine2 = document.getElementById('line-two');

  refreshButton = document.getElementById('refresh');

  fetch('/api/couplet').then(function (res) {
    res.json().then(function (data) {
      document.body.className = null;
      coupletLine1.textContent = data.first;
      coupletLine2.textContent = data.second;

      refreshButton.classList.remove('hidden');
    });
  });
}

function testPull() {
  if (document.body.className == '') {
    document.body.className = 'loading';
  } else {
    document.body.className = '';
  }
  document.getElementById('refresh').classList.remove('hidden');
}
