let baseUrl= 'http://localhost:8080';

document.addEventListener('DOMContentLoaded', () => {
  document.getElementById('form').addEventListener('submit', () => {
    event.preventDefault();
    let form= event.currentTarget;
    //onclick="window.location.href= './index.html';"
    let formData= new FormData();
    try {
      formData.append('agents', document.getElementById('agents').files[0]);
      formData.append('routes', document.getElementById('routes').files[0]);
    } catch (error) {
      console.log("AY");
    }
    fetch(`${baseUrl}/agents/massiveLoad`, {
      method: 'POST',
      body: formData
    }).then(response => response.text())
    .then(data => {
      console.log(data);
      window.location.href= './agents.html';
    })
  });
});

function triggerValidation(el) {
  let regex = new RegExp("(.*?)\.(csv)$");
  if (!(regex.test(el.value.toLowerCase()))) {
    el.value = '';
    alert('Seleccione un archivo con formato .csv');
  }
}