/* global $ */

import Vue from 'vue'
import List from 'src/components/List'

const fetchMock = require('fetch-mock')

describe('List.vue', () => {
  it('should display status list', (done) => {
    let status = {name: 'Name1', yesterday: 'Yesterday1', today: 'Today1', impediments: 'Impediments1'}

    fetchMock.get('/api/status/all', () => {
      return [status]
    })

    const vm = new Vue(List).$mount()

    setTimeout(() => {
      expect($(vm.$el).find('.name').text()).toEqual(status.name)
      expect($(vm.$el).find('.yesterday').text()).toEqual(status.yesterday)
      expect($(vm.$el).find('.today').text()).toEqual(status.today)
      expect($(vm.$el).find('.impediments').text()).toEqual(status.impediments)

      done()
    })
  })
})
