#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
#include <time.h>

#define FIREBASE_HOST "jawdata-h10.firebaseio.com"
//#define FIREBASE_AUTH "token_or_secret"
#define WIFI_SSID "HUAWEI P20 lite"
#define WIFI_PASSWORD "apinabeibe534"

int sensorPin = A0;
int sensorValue = 0;
int ledPin = 13;

void setup() {
  Serial.begin(9600);
  pinMode(ledPin, OUTPUT);
  
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connecting");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("connected: ");
  Serial.println(WiFi.localIP());
  
  //Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  Firebase.begin(FIREBASE_HOST);

  configTime(3 * 3600, 0, "pool.ntp.org", "time.nist.gov");
  Serial.println("\nWaiting for time");
  while (!time(nullptr)) {
    Serial.print(".");
    delay(1000);
  }
  time(NULL);
  delay(1000);
  Serial.println("");
  
}

int n = 0;
StaticJsonBuffer<200> jsonBuffer;

void loop() {
  // read the value from the sensor:
  sensorValue = analogRead(sensorPin);
  Serial.println(sensorValue);

  char timenow[20];
  time_t now = time(NULL);
  strftime(timenow, 20, "%Y-%m-%d %H:%M:%S", localtime(&now));
  Serial.println(timenow);
  
  JsonObject& object1 = jsonBuffer.createObject();
  object1[timenow] = sensorValue;

  String name2 = Firebase.push("sensorlogs3", object1);
  
  if (Firebase.failed()) {
      Serial.print("pushing /sensorslogs3 failed:");
      Serial.println(Firebase.error());  
      return;
  }
  Serial.print("pushed: /sensorlogs3/");
  Serial.println(name2);

  delay(10000);
}
