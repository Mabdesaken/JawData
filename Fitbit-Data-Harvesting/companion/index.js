/*
 * Entry point for the companion app
 */
import {postData} from '../common/firebase.js'

console.log("Companion code started");

import * as messaging from "messaging";

messaging.peerSocket.onmessage = evt => {
  postData(evt.data);
}

