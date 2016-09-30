/* global $ */

import Vue from 'vue'
import List from 'src/components/List'

const fetchMock = require('fetch-mock')

describe('List.vue', () => {
  it('should display status list', (done) => {
    fetchMock.get('/api/status/all', () => {
      return [{name: 'Name1', yesterday: 'Yesterday1', today: 'Today1', impediments: 'Impediments1'}]
    })

    const vm = new Vue(List).$mount()

    setTimeout(() => {
      expect($(vm.$el).find('.panel-heading').text()).toEqual('Name1')

      done()
    })
  })
})
