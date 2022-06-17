<template>
  <div class="footer">
    <div class="links">
    </div>
    <div class="copyright">
      Copyright © 2020 <a target="_blank" href="https://www.faqisheng.com/">法企胜</a> All rights reserved. SQYCMS 1.8
    </div>
  </div>
</template>

<script>
export default {
  name: 'GlobalFooter',
  data () {
    return {}
  }
}
</script>

<style lang="less" scoped>
.footer {
  padding: 15px;
  text-align: center;
  background: #fff;
  border-top:1px dashed #999 ;

  .links {

    a {
      color: rgba(0, 0, 0, 0.45);

      &:hover {
        color: rgba(0, 0, 0, 0.65);
      }

      &:not(:last-child) {
        margin-right: 40px;
      }
    }
  }
  .copyright {
    color: rgba(0, 0, 0, 0.45);
    font-size: 14px;
  }
}
</style>
