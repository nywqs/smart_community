<template>
  <a-modal
    title="新增文章"
    :width="900"
    :visible="visible"
    @ok="close"
    @cancel="close"
  >
    {{ data.title }}
  </a-modal>
</template>

<script>
export default {
  name: 'detail',
  data() {
    return {
      visible: false,
      data: {}
    }
  },
  methods: {
    show(record) {
      this.visible = true
      this.data = record
    },
    close() {
      this.visible = false
    }
  }
}
</script>

<style scoped>

</style>
