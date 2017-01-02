// var glavneKat = [ 4 ,3];
//

// console.log(troskovnik.name);
//
// var expenseCategories = troskovnik.expenseCategories;
// var expenseItems = troskovnik.expenseItems;
// var glavneKategorije = [];



  function getGlavne(expenseCategories,indexi){
     var glavne=[];

     for (var i = 0 ; i< expenseCategories.length;i++){
        for (var j in indexi){
            if (expenseCategories[i].id == indexi[j])
                glavne.push(expenseCategories[i]);
        }
      }

      return glavne;


  }


    function processList(lista, caca){
      console.log("funkcija se izvela  bar jednom");
      for (var i in lista){

          var cvor = {
            parent: caca,
             text: {
                 name: lista[i].name,
                 title: "Hello jazavci",
                 contact: "Tel: 01 213 123 134",
             }
          }

          chart_config.push(cvor);

          //sada ulazimo u potkategorije

          if (lista[i].subCategories.length == 0){
              console.log(lista[i].subCategories.length);
              console.log("nema potkategorija");
              }
          else{
            console.log("ulazimo u rekurziju");
            processList(lista[i].subCategories , cvor);

          }

      }

    }





    // Antoher approach, same result
    // JSON approach

/*
    var chart_config = {
        chart: {
            container: "#basic-example",

            connectors: {
                type: 'step'
            },
            node: {
                HTMLclass: 'nodeExample1'
            }
        },
        nodeStructure: {
            text: {
                name: "Mark Hill",
                title: "Chief executive officer",
                contact: "Tel: 01 213 123 134",
            },
            image: "../headshots/2.jpg",
            children: [
                {
                    text:{
                        name: "Joe Linux",
                        title: "Chief Technology Officer",
                    },
                    stackChildren: true,
                    image: "../headshots/1.jpg",
                    children: [
                        {
                            text:{
                                name: "Ron Blomquist",
                                title: "Chief Information Security Officer"
                            },
                            image: "../headshots/8.jpg"
                        },
                        {
                            text:{
                                name: "Michael Rubin",
                                title: "Chief Innovation Officer",
                                contact: "we@aregreat.com"
                            },
                            image: "../headshots/9.jpg"
                        }
                    ]
                },
                {
                    stackChildren: true,
                    text:{
                        name: "Linda May",
                        title: "Chief Business Officer",
                    },
                    image: "../headshots/5.jpg",
                    children: [
                        {
                            text:{
                                name: "Alice Lopez",
                                title: "Chief Communications Officer"
                            },
                            image: "../headshots/7.jpg"
                        },
                        {
                            text:{
                                name: "Mary Johnson",
                                title: "Chief Brand Officer"
                            },
                            image: "../headshots/4.jpg"
                        },
                        {
                            text:{
                                name: "Kirk Douglas",
                                title: "Chief Business Development Officer"
                            },
                            image: "../headshots/11.jpg"
                        }
                    ]
                },
                {
                    text:{
                        name: "John Green",
                        title: "Chief accounting officer",
                        contact: "Tel: 01 213 123 134",
                    },
                    image: "../headshots/6.jpg",
                    children: [
                        {
                            text:{
                                name: "Erica Reel",
                                title: "Chief Customer Officer"
                            },
                            link: {
                                href: "http://www.google.com"
                            },
                            image: "../headshots/10.jpg"
                        }
                    ]
                }
            ]
        }
    };

*/
