let postData = (dataToPost) => {
  console.log(dataToPost)
  fetch( `https://jawdata-h10.firebaseio.com/Fitbit.json`,
        {
                method: 'POST',
                mode: 'cors',
                headers: {
                  "Content-Type": "application/json; charset=utf-8",
                },
                body: dataToPost
        }
        ).then(function(data){ console.log('Request succeeded with response', data);})
        .catch(function(error){ console.log(error.message);});
}
    

export {postData}