import Vue from 'vue'
import Submit from 'src/components/Submit'

const fetchMock = require('fetch-mock')

import store from 'src/store'

describe('Submit.vue', () => {
  it('should submit status', (done) => {
    fetchMock.post('/api/status', (url, opts) => {
      return opts
    })

    const vm = new Vue(Submit).$mount()

    vm.yourName = 'My Name'
    vm.yesterday = 'Finished a task'
    vm.today = 'Starting a task'

    vm.onSubmit()

    setTimeout(() => {
      expect(store.getStatusList()).toContain({name: 'My Name', yesterday: 'Finished a task', today: 'Starting a task', impediments: null})

      let postJson = JSON.parse(fetchMock.lastOptions('/api/status').body)

      expect(postJson.name).toEqual('My Name')

      done()
    })
  })
})
