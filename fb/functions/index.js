const functions = require('firebase-functions');
const admin = require('firebase-admin')
const { WebhookClient } =require('dialogflow-fulfillment')

process.env.DEBUG = 'dialogflow:*'
admin.initializeApp(functions.config().firebase)
const db = admin.firestore()

exports.dialogflowFirebaseFullfillment = functions.https.onRequest((req, res) => {
    const agent = new WebhookClient({ req, res })

    const writeToDb = (agent) => {
        const databaseEntry = agent.parameters.databaseEntry

        const dialogflowAgentRef = db.collection('dialogflow').doc('agent')
        return db.runTransaction(t => {
            t.set(dialogflowAgentRef, { entry: databaseEntry })
            return  Promise.resolve('Write complete')
        }).then(doc => {
            agent.add('Wrote "${databaseEntry}" to the Firestore db')
        }).catch(err => {
            console.log(`Error writing to Firestore: ${err}`)
            agent.add('Failed to write "${databaseEntry}" to Firestore database')
        })
    }

    const readFromDb = (agent) => {
        const dialogflowAgentDoc = db.collection('dialogflow').doc('agent')

        return dialogflowAgentDoc.get()
            .then(doc => {
                if (!doc.exists) {
                    agent.add('No data found')
                } else {
                    agent.add(doc.data().entry)
                }
                return Promise.resolve('Read complete')
            }).catch(() => {
                agent.add('Error reading entry from database.')
                agent.add('Please add entry to database,')
            })
    }

    let intentMap = new Map()
    intentMap.set('ReadFromFirestore', readFromDb)
    intentMap.set('WriteToFirestore', writeToDb)
    agent.handleRequest(intentMap)
})

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
