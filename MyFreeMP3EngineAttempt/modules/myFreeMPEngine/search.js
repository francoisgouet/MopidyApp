function ready(){
  var e = document.getElementById('tracks-template');
console.log(e);
const tracksTemplateSource = document.getElementById('tracks-template').innerHTML;
const tracksTemplate = Handlebars.compile(tracksTemplateSource);
console.log("fdsfsdfsdfsd");
const $tracks = $('#tracks-container');

const getTopTracks = $.get('https://api.napster.com/v2.1/tracks/top?apikey=ZTk2YjY4MjMtMDAzYy00MTg4LWE2MjYtZDIzNjJmMmM0YTdm');
console.log("sfsdfsdfsdfsdfdsfdsfdsfdsfdsfsdfdsfdsfdsfsdf");
getTopTracks
  .then((response) => {
    console.log(response);
    $tracks.html(tracksTemplate(response));
  });
}
ready();
