import firebase from 'firebase'
var config = {
  apiKey: '?',
  authDomain: '?',
  databaseURL: '?',
  projectId: '?',
  storageBucket: '?',
  messagingSenderId: '?'
}
firebase.initializeApp(config)

const db = firebase.database()

export default db