/*global fetch*/

import { EventEmitter } from 'events'
import 'whatwg-fetch'

const store = new EventEmitter()

let statusList = []

let addStatus = (newStatus) => {
  if (!statusList) {
    statusList = []
  }

  statusList.push(newStatus)

  store.emit('statusList-updated')
}

store.newStatus = (status) => {
  fetch('/api/status', {
    method: 'POST',
    body: JSON.stringify(status),
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    }
  }).then((res) => {
    return res.json()
  }).then((json) => {
    let newStatus = json

    addStatus(newStatus)
  })
}

store.loadAll = () => {
  return fetch('/api/status/all').then((res) => {
    return res.json().then((json) => {
      statusList = json

      return statusList
    })
  })
}

store.getStatusList = () => {
  return statusList
}

export default store
