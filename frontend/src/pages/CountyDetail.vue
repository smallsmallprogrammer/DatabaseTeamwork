<template>
  <div class="county-detail-container">
    <!-- 顶部导航 -->
    <header class="detail-header">
      <el-button @click="$router.push('/')" icon="ArrowLeft" />
      <h1>{{ countyInfo.name }} 脱贫详情</h1>
    </header>

    <!-- 基础信息卡片 -->
    <div class="basic-info-container">
      <el-card>
        <div class="info-grid">
          <div class="info-item">
            <span class="info-label">所属省份</span>
            <span class="info-value">{{ countyInfo.province }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">摘帽年份</span>
            <span class="info-value">{{ countyInfo.delistingYear || '未摘帽' }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">总人口(万人)</span>
            <span class="info-value">{{ countyInfo.population }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">总面积(平方公里)</span>
            <span class="info-value">{{ countyInfo.area }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">主要产业</span>
            <span class="info-value">{{ countyInfo.mainIndustries.join(', ') }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">脱贫主要措施</span>
            <span class="info-value">{{ countyInfo.povertyMeasures.join(', ') }}</span>
          </div>
        </div>
      </el-card>
    </div>

    <!-- 经济指标时间序列图表 -->
    <div class="economic-chart-container">
      <el-card>
        <div slot="header">
          <h2>经济指标变化趋势 (2016-2020)</h2>
        </div>
        <div id="economic-trend" class="chart-container"></div>
      </el-card>
    </div>

    <!-- 产业结构分析 -->
    <div class="industry-analysis-container">
      <div class="industry-pie-container">
        <el-card>
          <div slot="header">
            <h2>2020年三大产业占比</h2>
          </div>
          <div id="industry-pie" class="chart-container"></div>
        </el-card>
      </div>
      <div class="industry-trend-container">
        <el-card>
          <div slot="header">
            <h2>产业结构变化趋势</h2>
          </div>
          <div id="industry-trend" class="chart-container"></div>
        </el-card>
      </div>
    </div>

    <!-- 农产品产量表格 -->
    <div class="agriculture-table-container">
      <el-card>
        <div slot="header">
          <h2>农产品产量年度对比 (单位: 万吨)</h2>
        </div>
        <el-table :data="agricultureData" border>
          <el-table-column prop="product" label="农产品" />
          <el-table-column prop="2016" label="2016年" />
          <el-table-column prop="2017" label="2017年" />
          <el-table-column prop="2018" label="2018年" />
          <el-table-column prop="2019" label="2019年" />
          <el-table-column prop="2020" label="2020年" />
          <el-table-column label="增长率" formatter="formatGrowthRate" />
        </el-table>
      </el-card>
    </div>

    <!-- 工业品产量数据 -->
    <div class="industry-output-container">
      <el-card>
        <div slot="header">
          <h2>工业品产量数据 (2020年)</h2>
        </div>
        <div id="industry-output" class="chart-container"></div>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import * as echarts from 'echarts';
import { useRoute } from 'vue-router';

const route = useRoute();
const countyName = route.params.name as string;

// 模拟县域数据 - 实际项目中应从API获取
const countyInfo = ref({
  name: countyName || '某贫困县',
  province: '贵州省',
  delistingYear: 2020,
  population: 58.2,
  area: 2874,
  mainIndustries: ['农业', '旅游业', '轻工业'],
  povertyMeasures: ['产业扶持', '教育扶贫', '就业培训', '异地搬迁']
});

// 经济指标数据
const economicData = ref({
  years: [2016, 2017, 2018, 2019, 2020],
  gdp: [86.5, 95.2, 108.7, 120.5, 135.8], // 单位: 亿元
  perCapitaIncome: [6850, 7580, 8320, 9250, 10560], // 单位: 元
  fiscalRevenue: [6.2, 7.1, 8.5, 9.8, 11.2] // 单位: 亿元
});

// 产业结构数据
const industryData = ref({
  years: [2016, 2017, 2018, 2019, 2020],
  primary: [35, 32, 29, 26, 23], // 第一产业占比
  secondary: [25, 27, 29, 31, 33], // 第二产业占比
  tertiary: [40, 41, 42, 43, 44] // 第三产业占比
});

// 2020年产业占比
const industryPieData = ref([
  { name: '第一产业', value: 23 },
  { name: '第二产业', value: 33 },
  { name: '第三产业', value: 44 }
]);

// 农产品数据
const agricultureData = ref([
  { product: '粮食', 2016: 18.5, 2017: 19.2, 2018: 20.1, 2019: 21.3, 2020: 22.5 },
  { product: '蔬菜', 2016: 12.3, 2017: 13.5, 2018: 15.2, 2019: 16.8, 2020: 18.5 },
  { product: '水果', 2016: 5.2, 2017: 6.1, 2018: 7.5, 2019: 8.8, 2020: 10.2 },
  { product: '肉类', 2016: 3.8, 2017: 4.2, 2018: 4.5, 2019: 4.9, 2020: 5.3 }
]);

// 工业品数据
const industryOutputData = ref([
  { name: '发电量', value: 12.5 },
  { name: '水泥', value: 8.3 },
  { name: '化肥', value: 3.2 },
  { name: '食品加工', value: 15.6 },
  { name: '纺织', value: 4.8 },
  { name: '电子产品', value: 2.1 }
]);

// 图表实例
let economicTrendChart: echarts.ECharts;
let industryPieChart: echarts.ECharts;
let industryTrendChart: echarts.ECharts;
let industryOutputChart: echarts.ECharts;

// 初始化经济指标趋势图
const initEconomicTrend = () => {
  const dom = document.getElementById('economic-trend');
  if (dom) {
    economicTrendChart = echarts.init(dom);
    
    const option = {
      tooltip: {
        trigger: 'axis'
      },
      legend: {
        data: ['GDP(亿元)', '人均可支配收入(元)', '财政收入(亿元)']
      },
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: {
        type: 'category',
        boundaryGap: false,
        data: economicData.value.years
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          name: 'GDP(亿元)',
          type: 'line',
          data: economicData.value.gdp,
          yAxisIndex: 0
        },
        {
          name: '人均可支配收入(元)',
          type: 'line',
          data: economicData.value.perCapitaIncome,
          yAxisIndex: 1
        },
        {
          name: '财政收入(亿元)',
          type: 'line',
          data: economicData.value.fiscalRevenue,
          yAxisIndex: 0
        }
      ],
      yAxis: [
        {
          type: 'value',
          name: '亿元'
        },
        {
          type: 'value',
          name: '元',
          position: 'right',
          offset: 0,
          axisLine: {
            lineStyle: {
              color: '#ff4500'
            }
          },
          axisLabel: {
            formatter: '{value}'
          }
        }
      ]
    };
    
    economicTrendChart.setOption(option);
  }
};

// 初始化产业结构饼图
const initIndustryPie = () => {
  const dom = document.getElementById('industry-pie');
  if (dom) {
    industryPieChart = echarts.init(dom);
    
    const option = {
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 10
      },
      series: [
        {
          name: '产业占比',
          type: 'pie',
          radius: ['40%', '70%'],
          avoidLabelOverlap: false,
          itemStyle: {
            borderRadius: 10,
            borderColor: '#fff',
            borderWidth: 2
          },
          label: {
            show: false,
            position: 'center'
          },
          emphasis: {
            label: {
              show: true,
              fontSize: 18,
              fontWeight: 'bold'
            }
          },
          labelLine: {
            show: false
          },
          data: industryPieData.value
        }
      ]
    };
    
    industryPieChart.setOption(option);
  }
};

// 初始化产业结构趋势图
const initIndustryTrend = () => {
  const dom = document.getElementById('industry-trend');
  if (dom) {
    industryTrendChart = echarts.init(dom);
    
    const option = {
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'shadow'
        }
      },
      legend: {
        data: ['第一产业', '第二产业', '第三产业']
      },
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: {
        type: 'category',
        data: industryData.value.years
      },
      yAxis: {
        type: 'value',
        axisLabel: {
          formatter: '{value}%'
        }
      },
      series: [
        {
          name: '第一产业',
          type: 'bar',
          stack: 'total',
          emphasis: {
            focus: 'series'
          },
          data: industryData.value.primary
        },
        {
          name: '第二产业',
          type: 'bar',
          stack: 'total',
          emphasis: {
            focus: 'series'
          },
          data: industryData.value.secondary
        },
        {
          name: '第三产业',
          type: 'bar',
          stack: 'total',
          emphasis: {
            focus: 'series'
          },
          data: industryData.value.tertiary
        }
      ]
    };
    
    industryTrendChart.setOption(option);
  }
};

// 初始化工业品产量图表
const initIndustryOutput = () => {
  const dom = document.getElementById('industry-output');
  if (dom) {
    industryOutputChart = echarts.init(dom);
    
    const option = {
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'shadow'
        }
      },
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: {
        type: 'value',
        boundaryGap: [0, 0.01]
      },
      yAxis: {
        type: 'category',
        data: industryOutputData.value.map(item => item.name)
      },
      series: [
        {
          name: '产量(万吨)',
          type: 'bar',
          data: industryOutputData.value.map(item => item.value)
        }
      ]
    };
    
    industryOutputChart.setOption(option);
  }
};

// 格式化增长率
const formatGrowthRate = (row: any) => {
  const growth = ((row[2020] - row[2016]) / row[2016] * 100).toFixed(2);
  return `${growth}%`;
};

// 初始化
onMounted(() => {
  initEconomicTrend();
  initIndustryPie();
  initIndustryTrend();
  initIndustryOutput();
  
  // 窗口大小变化时重绘图表
  window.addEventListener('resize', () => {
    economicTrendChart?.resize();
    industryPieChart?.resize();
    industryTrendChart?.resize();
    industryOutputChart?.resize();
  });
});
</script>

<style scoped>
.county-detail-container {
  width: 100%;
  padding: 20px;
  box-sizing: border-box;
  background-color: #f5f7fa;
}

.detail-header {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.detail-header h1 {
  margin: 0 0 0 10px;
  color: #1f2329;
}

.basic-info-container {
  margin-bottom: 20px;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 15px;
  padding: 10px 0;
}

.info-item {
  display: flex;
  flex-direction: column;
}

.info-label {
  font-size: 14px;
  color: #606266;
}

.info-value {
  font-size: 16px;
  font-weight: 500;
  color: #303133;
  margin-top: 5px;
}

.economic-chart-container {
  margin-bottom: 20px;
  height: 400px;
}

.industry-analysis-container {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
  margin-bottom: 20px;
  height: 400px;
}

.industry-pie-container, .industry-trend-container {
  height: 100%;
}

.agriculture-table-container {
  margin-bottom: 20px;
}

.industry-output-container {
  height: 400px;
}

.chart-container {
  width: 100%;
  height: calc(100% - 50px);
}

.el-card {
  height: 100%;
  display: flex;
  flex-direction: column;
}
</style>