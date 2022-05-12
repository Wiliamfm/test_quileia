document.addEventListener('DOMContentLoaded', () => {
  loadRoutes('http://localhost:8080/agents');
  document.querySelector('form').addEventListener('submit', () => {
    //event.preventDefault();
    let form= event.currentTarget;
    console.log(form);
    createAgent('http://localhost:8080/agents', form['code'].value, form['full_name'].value, form['experience_year'].value, form['transit_code'].value);
  })
  document.getElementById('btn_form_cancel').addEventListener('click', () => {
    document.querySelector('form')['code'].removeAttribute('readonly');
  });
});

function createAgent(url, code, fullName, exp, tranCode){
  data= {
    code: code,
    full_name: fullName,
    experience_year: exp,
    transit_code: tranCode
  };
  fetch(url, {
    method: "POST",
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(data)
  }).then(response => response.json())
  .then(data => {
    console.log(data);
  }).catch(err => {
    console.log('Error creating the agent: ', err);
  });
}

function loadRoutes(url){
  fetch(url)
  .then(response => response.json())
  .then(data => {
    //console.log(data);
    let tbody= document.querySelector('tbody');
    tbody.textContent= '';
    data.forEach(r => {
      //console.log(a);
      let tr= document.createElement('tr');
      for(let p in r){
        let td= document.createElement('td');
        if(r[p]){
          td.textContent= r[p];
        }else{
          td.textContent= 'Sin datos';
        }
        tr.appendChild(td);
      }
      let ul = document.createElement('ul');
      ul.appendChild(createActionButton('Editar', () => {
        editAgent(r);
      }));
      ul.appendChild(createActionButton('Eliminar', () => {
        deleleteAgent(`http://localhost:8080/agents/${r['code']}`);
      }));
      ul.appendChild(createActionButton('Asignar vía', () => {
        addRoute();
      }));
      tr.appendChild(ul);
      tbody.appendChild(tr);
    })
  }).catch(err => {
    console.log('Error while loading agents: ', err);
  });
}

function createActionButton(text, f){
  let btn= document.createElement('button');
  btn.textContent= text;
  btn.onclick= f;
  return btn;
}

function deleleteAgent(url){
  fetch(url, {
    method: 'POST',
  }).then(response => response.json())
  .then(data => {
    alert(`The agent ${data['fullName']} was deleted`);
    loadRoutes('http://localhost:8080/agents');
  }).catch(err => {
    console.log(`Error deleting the agent ${data['fullName']} with error ${err}`);
  })
}

function editAgent(agent){
  let form= document.querySelector('form');
  //console.log(agent);
  form['code'].value= agent['code'];
  form['code'].setAttribute('readonly', true);
  form['full_name'].value= agent['fullName'];
  form['exp'].value= agent['experienceYear'];
  form['transit_code'].value= agent['transitCode'];
  form['code'].focus();
}

function addRoute(agent){

}