<script setup lang="ts">
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from '@/components/ui/card';
import { use } from 'echarts/core';
import { CanvasRenderer } from 'echarts/renderers';
import { PieChart, BarChart, LineChart } from 'echarts/charts';
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent,
  DatasetComponent,
  DataZoomComponent,
} from 'echarts/components';
import VChart, { THEME_KEY } from 'vue-echarts';
import { ref, provide, onMounted } from 'vue';

use([
  CanvasRenderer,
  PieChart,
  BarChart,
  LineChart,
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent,
  DatasetComponent,
  DataZoomComponent,
]);

provide(THEME_KEY, 'light');

// 兼职类型分布
const jobTypeOption = ref({
  title: {
    text: '兼职类型分布',
    left: 'center',
  },
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b}: {c} ({d}%)',
  },
  legend: {
    orient: 'vertical',
    right: 10,
    top: 'center',
    data: ['家教', '促销', '服务员', '实习', '其他'],
  },
  series: [
    {
      name: '兼职类型',
      type: 'pie',
      radius: ['40%', '70%'],
      center: ['40%', '50%'],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 10,
        borderColor: '#fff',
        borderWidth: 2,
      },
      label: {
        show: false,
        position: 'center',
      },
      emphasis: {
        label: {
          show: true,
          fontSize: '18',
          fontWeight: 'bold',
        },
      },
      labelLine: {
        show: false,
      },
      data: [
        { value: 35, name: '家教' },
        { value: 28, name: '促销' },
        { value: 45, name: '服务员' },
        { value: 32, name: '实习' },
        { value: 20, name: '其他' },
      ],
    },
  ],
});

// 薪资范围分布
const salaryOption = ref({
  title: {
    text: '薪资范围分布 (元/小时)',
    left: 'center',
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'shadow',
    },
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true,
  },
  xAxis: {
    type: 'category',
    data: ['10-20', '20-30', '30-50', '50-80', '80+'],
    axisLabel: {
      interval: 0,
      rotate: 0,
    },
  },
  yAxis: {
    type: 'value',
    name: '岗位数量',
  },
  series: [
    {
      name: '岗位数量',
      type: 'bar',
      data: [45, 78, 65, 32, 15],
      itemStyle: {
        color: function(params) {
          const colorList = ['#c23531','#2f4554','#61a0a8','#d48265','#91c7ae'];
          return colorList[params.dataIndex];
        },
        borderRadius: [4, 4, 0, 0],
      },
    },
  ],
});

// 兼职时间趋势
const timeTrendOption = ref({
  title: {
    text: '兼职需求趋势 (近6个月)',
    left: 'center',
  },
  tooltip: {
    trigger: 'axis',
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true,
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: ['1月', '2月', '3月', '4月', '5月', '6月'],
  },
  yAxis: {
    type: 'value',
    name: '岗位数量',
  },
  series: [
    {
      name: '岗位数量',
      type: 'line',
      data: [120, 132, 101, 134, 90, 230],
      smooth: true,
      lineStyle: {
        width: 3,
        color: '#2f4554',
      },
      itemStyle: {
        color: '#61a0a8',
        borderWidth: 2,
      },
      areaStyle: {
        color: {
          type: 'linear',
          x: 0,
          y: 0,
          x2: 0,
          y2: 1,
          colorStops: [
            { offset: 0, color: 'rgba(47, 69, 84, 0.3)' },
            { offset: 1, color: 'rgba(47, 69, 84, 0.1)' },
          ],
        },
      },
    },
  ],
});

// 热门技能要求
const skillsOption = ref({
  title: {
    text: '热门技能要求',
    left: 'center',
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'shadow',
    },
  },
  grid: {
    left: '25%',
    right: '5%',
    bottom: '3%',
    top: '15%',
  },
  xAxis: {
    type: 'value',
    show: false,
  },
  yAxis: {
    type: 'category',
    data: ['沟通能力', '办公软件', 'PS/PR', '外语', '编程', '摄影', '驾驶'],
    axisLine: { show: false },
    axisTick: { show: false },
    axisLabel: {
      margin: 20,
      fontSize: 14,
    },
  },
  series: [
    {
      name: '需求占比',
      type: 'bar',
      data: [95, 85, 65, 55, 45, 30, 25],
      itemStyle: {
        color: function(params) {
          const colorList = ['#c23531','#2f4554','#61a0a8','#d48265','#91c7ae','#749f83','#ca8622'];
          return colorList[params.dataIndex];
        },
        borderRadius: [0, 4, 4, 0],
      },
      label: {
        show: true,
        position: 'right',
        formatter: '{c}%',
      },
    },
  ],
});

// 响应式调整图表大小
const chartRefs = ref<{ [key: string]: any }>({});

const handleResize = () => {
  Object.keys(chartRefs.value).forEach(chartName => {
    chartRefs.value[chartName].resize();
  });};

onMounted(() => {
  window.addEventListener('resize', handleResize);
  return () => {
    window.removeEventListener('resize', handleResize);
  };
});
</script>

<template>
  <div class="p-6">
    <h1 class="text-2xl font-bold mb-6">大学生兼职数据看板</h1>
    
    <div class="grid gap-6 grid-cols-1 lg:grid-cols-2">
      <!-- 兼职类型分布 -->
      <Card>
        <CardHeader>
          <CardTitle>兼职类型分布</CardTitle>
          <CardDescription>各类兼职岗位的占比情况</CardDescription>
        </CardHeader>
        <CardContent class="h-96">
          <v-chart
            ref="jobTypeChart"
            :option="jobTypeOption"
            :autoresize="true"
            style="width: 100%; height: 100%;"
            @finished="() => { chartRefs.jobTypeChart = $event; }"
          />
        </CardContent>
      </Card>

      <!-- 薪资范围分布 -->
      <Card>
        <CardHeader>
          <CardTitle>薪资范围分布</CardTitle>
          <CardDescription>不同薪资区间的岗位数量</CardDescription>
        </CardHeader>
        <CardContent class="h-96">
          <v-chart
            ref="salaryChart"
            :option="salaryOption"
            :autoresize="true"
            style="width: 100%; height: 100%;"
            @finished="() => { chartRefs.salaryChart = $event; }"
          />
        </CardContent>
      </Card>

      <!-- 兼职时间趋势 -->
      <Card class="lg:col-span-2">
        <CardHeader>
          <CardTitle>兼职需求趋势</CardTitle>
          <CardDescription>近6个月兼职岗位数量变化</CardDescription>
        </CardHeader>
        <CardContent class="h-96">
          <v-chart
            ref="timeTrendChart"
            :option="timeTrendOption"
            :autoresize="true"
            style="width: 100%; height: 100%;"
            @finished="() => { chartRefs.timeTrendChart = $event; }"
          />
        </CardContent>
      </Card>

      <!-- 热门技能要求 -->
      <Card class="lg:col-span-2">
        <CardHeader>
          <CardTitle>热门技能要求</CardTitle>
          <CardDescription>岗位中常见的技能要求占比</CardDescription>
        </CardHeader>
        <CardContent class="h-96">
          <v-chart
            ref="skillsChart"
            :option="skillsOption"
            :autoresize="true"
            style="width: 100%; height: 100%;"
            @finished="() => { chartRefs.skillsChart = $event; }"
          />
        </CardContent>
      </Card>
    </div>
  </div>
</template>

<style scoped>
/* 添加响应式布局 */
@media (max-width: 1024px) {
  .grid {
    grid-template-columns: 1fr;
  }
}
</style>
