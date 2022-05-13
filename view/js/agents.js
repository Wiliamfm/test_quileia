let baseUrl= 'http://localhost:8080';

document.addEventListener('DOMContentLoaded', () => {
  loadAgents('http://localhost:8080/agents');
  document.getElementById('form_agent').addEventListener('submit', () => {
    event.preventDefault();
    let form= event.currentTarget;
    //console.log(form);
    createRoute('http://localhost:8080/agents', form['code'].value, form['full_name'].value, form['experience_year'].value, form['transit_code'].value);
  })
  document.getElementById('btn_form_cancel').addEventListener('click', () => {
    document.getElementById('form_agent')['code'].removeAttribute('readonly');
  });
  document.getElementById('btn_form_route_cancel').addEventListener('click', () => {
    document.getElementById('div_form_route').style.display= 'none';
    document.getElementById('div_agents').style.display= 'block';
  });
});

function createRoute(url, code, fullName, exp, tranCode){
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
    if(data){
      console.log(data);
      document.getElementById('form_agent').reset();
      loadAgents('http://localhost:8080/agents');
    }else{
      alert(data);
    }
  }).catch(err => {
    console.log('Error creating the agent: ', err);
  });
}

function loadAgents(url){
  fetch(url)
  .then(response => response.json())
  .then(data => {
    //console.log(data);
    let tbody= document.querySelector('tbody');
    tbody.textContent= '';
    data.forEach(a => {
      //console.log(a);
      let tr= document.createElement('tr');
      for(let p in a){
        let td= document.createElement('td');
        if(p === 'transitRoute' && a[p]){
          td.textContent= `${a[p]['routeType']['routeType']} - ${a[p]['type']['type']} ${a[p]['number']}`;
        }else{
          if(a[p]){
            td.textContent= a[p];
          }else{
            td.textContent= 'Sin datos';
          }
        }
        tr.appendChild(td);
      }
      let ul = document.createElement('ul');
      ul.appendChild(createActionButton('Editar', () => {
        editRoute(a);
      }));
      ul.appendChild(createActionButton('Eliminar', () => {
        deleteRoute(`http://localhost:8080/agents/${a['code']}`);
      }));
      ul.appendChild(createActionButton('Asignar vía', () => {
        addRoute(a);
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

function deleteRoute(url){
  fetch(url, {
    method: 'POST',
  }).then(response => response.json())
  .then(data => {
    alert(`The agent ${data['fullName']} was deleted`);
    loadAgents('http://localhost:8080/agents');
  }).catch(err => {
    console.log(`Error deleting the agent ${data['fullName']} with error ${err}`);
  })
}

function editRoute(agent){
  let form= document.getElementById('form_agent');
  //console.log(agent);
  form['code'].value= agent['code'];
  form['code'].setAttribute('readonly', true);
  form['full_name'].value= agent['fullName'];
  form['exp'].value= agent['experienceYear'];
  form['transit_code'].value= agent['transitCode'];
  form['code'].focus();
}

function addRoute(agent){
  document.getElementById('div_form_route').style.display= 'block';
  let form= document.getElementById('form_route');
  document.getElementById('div_agents').style.display= 'none';
  loadRouteTypes(baseUrl+'/route_types');
  loadStreetTypes(baseUrl+'/street_types');
  form.addEventListener('submit', () => {
    event.preventDefault();
    fetch(baseUrl+'/agents/'+agent['code']+'/routes', {
      method: 'POST',
      headers:{
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        route_type: form['routeType'].value,
        street_type: form['streetType'].value,
        number: form['number'].value
      })
    }).then(response => response.json())
    .then(data => {
      console.log(data);
      window.location.reload();
    }).catch(err => {
      alert(`No se pudo agregar la ruta al agente: ${agent['fullName']}`);
      console.log(`Error adding route to agent ${agent['fullName']} with error: ${err}`);
    })
  });
}

function loadRouteTypes(url){
  fetch(url)
  .then(response => response.json())
  .then(data => {
    //console.log(data);
    let dataList= document.getElementById('route_types');
    data.forEach(rt => {
      let option= document.createElement('option');
      option.value= rt['routeType'];
      dataList.appendChild(option);
    });
  }).catch(err => {
    console.log(`Error getting route types: ${err}`);
  })
}

function loadStreetTypes(url){
  fetch(url)
  .then(response => response.json())
  .then(data => {
    //console.log(data);
    let dataList= document.getElementById('street_types');
    data.forEach(st => {
      let option= document.createElement('option');
      option.value= st['type'];
      dataList.appendChild(option);
    });
  }).catch(err => {
    console.log(`Error getting street types: ${err}`);
  })
}