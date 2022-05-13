let baseUrl= 'http://localhost:8080';

document.addEventListener('DOMContentLoaded', () => {
  loadRouteTypes(baseUrl+'/route_types');
  loadStreetTypes(baseUrl+'/street_types');
  loadRoutes(baseUrl+'/routes');
  document.querySelector('form').addEventListener('submit', () => {
    event.preventDefault();
    let form= event.currentTarget;
    //console.log(form);
    createRoute(baseUrl+'/routes', form['route_type'].value, form['street_type'].value, form['number'].value, form['con_lvl'].value, form['id'].value);
  })
  document.getElementById('btn_form_cancel').addEventListener('click', () => {
    document.querySelector('form')['id'].remove();
  });
});

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

function createRoute(url, routeType, streetType, number, conLvl, id){
  data= {
    route_type: routeType,
    street_type: streetType,
    number: number,
    contingency_level: conLvl,
    id: id
  };
  fetch(url, {
    method: "POST",
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(data)
  }).then(response => {
    return response.text();
  })
  .then(data => {
    console.log(`Data--------- --${data}--`);
    if(data){
      //console.log(data);
      loadRoutes(baseUrl+'/routes');
      document.getElementById('form').reset();
    }else{
      alert(`The route already exists`);
    }
  }).catch(err => {
    console.log('Error creating the route: ', err);
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
      //console.log(r);
      let tr= document.createElement('tr');
      let tdRouteType= document.createElement('td');
      let tdStreetType= document.createElement('td');
      let tdNumber= document.createElement('td');
      let tdConLvl= document.createElement('td');
      tdRouteType.textContent= r['routeType']['routeType'];
      tdStreetType.textContent= r['type']['type'];
      tdNumber.textContent= r['number'];
      tdConLvl.textContent= r['conLevel'];
      tr.appendChild(tdRouteType);
      tr.appendChild(tdStreetType);
      tr.appendChild(tdNumber);
      tr.appendChild(tdConLvl);
      let ul = document.createElement('ul');
      ul.appendChild(createActionButton('Editar', () => {
        editRoute(r);
      }));
      ul.appendChild(createActionButton('Eliminar', () => {
        deleteRoute(`${baseUrl}/routes/${r['id']}`);
      }));
      ul.appendChild(createActionButton('Mostrar historial', () => {
        showRouteHistory(r);
      }));
      tr.appendChild(ul);
      tbody.appendChild(tr);
    })
  }).catch(err => {
    console.log('Error while loading routes: ', err);
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
    alert(`The route ${data['id']} ${data['number']} was deleted`);
    loadRoutes(baseUrl+'/routes');
  }).catch(err => {
    console.log(`Error deleting the route ${data['id']} with error ${err}`);
  })
}

function editRoute(route){
  document.getElementById('btn_form_cancel').click();
  let form= document.querySelector('form');
  //console.log(agent);
  form['routeType'].value= route['routeType']['routeType'];
  form['streetType'].value= route['type']['type'];
  form['number'].value= route['number'];
  form['conLvl'].value= route['conLevel'];
  let inputId= document.createElement('input');
  inputId.setAttribute('name', 'id');
  inputId.type= 'hidden';
  inputId.value= route['id'];
  form.appendChild(inputId);
  form['routeType'].focus();
}

function addAgent(agent){

}

function showRouteHistory(route){
  window.sessionStorage.setItem('resource_id', route['id']);
  window.sessionStorage.setItem('resource', 'routes');
  window.location= './auditory.html';
}