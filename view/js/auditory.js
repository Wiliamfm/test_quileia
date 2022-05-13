let baseUrl= 'http://localhost:8080';

document.addEventListener('DOMContentLoaded', () => {
  loadData(`${baseUrl}/auditory/${window.sessionStorage.getItem('resource')}/${window.sessionStorage.getItem('resource_id')}`);
});

function loadData(url){
  fetch(url)
  .then(response => response.json())
  .then(data => {
    //console.log(data);
    let tbody= document.querySelector('tbody');
    tbody.textContent= '';
    data.forEach(d => {
      //console.log(d);
      let tr= document.createElement('tr');
      for(let p in d){
        let td= document.createElement('td');
        if(p === 'agent'){
          td.textContent= d[p]['fullName'];
          let td2= document.createElement('td');
          td2.textContent= d[p]['code']
          tr.appendChild(td);
          tr.appendChild(td2);
        }else{
          if(p === 'route'){
            td.textContent= d[p]['routeType']['routeType'] + " " +d[p]['type']['type'] + " # " +d[p]['number'];
            tr.appendChild(td);
          }else{
            if(d[p]){
              td.textContent= d[p].substring(0, 10);
            }else{
              td.textContent= "No data";
            }
            tr.appendChild(td);
          }
        }
        tbody.appendChild(tr);
      }
    })
  }).catch(err => {
    console.log('Error while loading agents: ', err);
  });
}