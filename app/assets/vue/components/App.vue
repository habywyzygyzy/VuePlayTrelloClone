<template>
   <div id="app">
     <h4>TEST</h4>
     <h1>{{ msg }}</h1>
      <!-- <Kanban :stages="statuses" :blocks="blocks" @update-block="updateBlock">
        <div v-for="stage in statuses" :slot="stage" :key="stage">
          <h2>
            {{ stage }}
            <a>+</a>
          </h2>
        </div>
        <div v-for="item in blocks" :slot="item.id" :key="item.id">
          <div>
            <strong>id:</strong> {{ item.id }}
          </div>
          <div>
            {{ item.title }}
          </div>
        </div>
      </Kanban> -->
    </div>
</template>

<script lang="ts">
  import Vue from 'vue';


  import { Type as OurButton } from './common/our-button.vue';

  Vue.component('our-button', require('./common/our-button.vue').default);
  Vue.component('our-js-button', require('./common/our-js-button.vue').default);

  // Since a JS component doesn't have type, we need to provide the type in order for Typescript to compile.
  interface OurJsButton extends Vue {
    testMessage(): void
  }

</script>

<script>
import faker from 'faker';
import { debounce } from 'lodash';
import Kanban from './components/Kanban';

Vue.use(Kanban);

export default {
  name: 'app',
  // components: {
  //   Kanban,
  // },
  data() {
    return {
      // statuses: ['on-hold', 'in-progress', 'needs-review', 'approved'],
      // blocks: [],
       msg: 'Welcome to Your Vue.js App'
    };
  }//,
  // mounted() {
  //   for (let i = 0; i <= 10; i += 1) {
  //     this.blocks.push({
  //       id: i,
  //       status: this.statuses[Math.floor(Math.random() * 4)],
  //       title: faker.company.bs(),
  //     });
  //   }
  // },
};
</script>

<style scoped lang="scss">
  @import './assets/kanban.scss';

  $on-hold: #FB7D44;
  $in-progress: #2A92BF;
  $needs-review: #F4CE46;
  $approved: #00B961;
  * {
    box-sizing: border-box;
  }
  body {
    background: #33363D;
    color: white;
    font-family: 'Lato';
    font-weight: 300;
    line-height: 1.5;
    -webkit-font-smoothing: antialiased;
  }
  .drag-column {
    .drag-column-header > div {
      width: 100%;
      h2 > a {
        float: right;
      }
    }
    &-on-hold {
      .drag-column-header,
      .is-moved,
      .drag-options {
        background: $on-hold;
      }
    }
    &-in-progress {
      .drag-column-header,
      .is-moved,
      .drag-options {
        background: $in-progress;
      }
    }
    &-needs-review {
      .drag-column-header,
      .is-moved,
      .drag-options{
        background: $needs-review;
      }
    }
    &-approved {
      .drag-column-header,
      .is-moved,
      .drag-options {
        background: $approved;
      }
    }
  }
  .section {
    padding: 20px;
    text-align: center;
    a {
      color: white;
      text-decoration: none;
      font-weight: 300;
    }
    h4 {
      font-weight: 400;
      a {
        font-weight: 600;
      }
    }
  }

</style>
