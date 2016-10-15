import Vue from 'vue'
import Submit from 'src/components/Submit'

const fetchMock = require('fetch-mock')

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
      let postJson = JSON.parse(fetchMock.lastOptions('/api/status').body)

      expect(postJson.name).toEqual('My Name')
      expect(postJson.yesterday).toEqual('Finished a task')
      expect(postJson.today).toEqual('Starting a task')

      done()
    })
  })
})
