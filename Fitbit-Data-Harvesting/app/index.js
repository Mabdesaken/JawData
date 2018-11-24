/*
 * Entry point for the watch app
 */

import {HeartRateSensor} from "heart-rate";
//import { BodyPresenceSensor } from "body-presence";
import {peerSocket} from "messaging";

//var bodyPresenceSensor = new BodyPresenceSensor();

let lastValueTimestamp = Date.now();

//bodyPresenceSensor.start();

var hrm = new HeartRateSensor();

hrm.onreading = function() {
  let currentHeartRate = hrm.heartRate;
  lastValueTimestamp = Date.now();
  let formattedMessageString = `{"timestamp" : ${lastValueTimestamp}, "heart_rate" : ${currentHeartRate}}`
  sendMessage(formattedMessageString);
}

hrm.start();

// bodyPresenceSensor.onreading = () => {
//   if (bodyPresenceSensor.present == '') {
//     // TAKE AND SEND HEAR RATE READINGS
    
//   } else {
    
//     if (hrm.activated) {
//       hrm.stop()
//     }
    
//     sendMessage("DEVICE OFF WRIST");
    
//   }
// }

let sendMessage = (messageString) => {
  if (peerSocket.readyState === peerSocket.OPEN) {
      peerSocket.send(messageString);
     // console.log("Message Sent");
  }
}





