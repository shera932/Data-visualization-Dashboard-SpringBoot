import React, { useEffect, useState } from 'react';
import { Bar, Line, Pie, Scatter, Bubble, Doughnut } from 'react-chartjs-2';
import { Chart, registerables } from 'chart.js';
import axios from 'axios';
import './VisDash.css';

// Register all Chart.js components
Chart.register(...registerables);

const VisDash = () => {
  const [chartData, setChartData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [filterCategory, setFilterCategory] = useState('endYear');
  const [filterValue, setFilterValue] = useState('');

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      setError(null);
      try {
        const response = await axios.get('http://localhost:8080/api/data/filter', {
          params: { [filterCategory]: filterValue },
        });
        setChartData(response.data);
      } catch (error) {
        console.error('Error fetching data:', error);
        setError('Error fetching data. Please try again.');
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [filterCategory, filterValue]);

  const handleCategoryChange = (e) => {
    setFilterCategory(e.target.value);
    setFilterValue('');
  };

  const handleValueChange = (e) => {
    setFilterValue(e.target.value);
  };

  const renderCharts = () => {
    if (!Array.isArray(chartData) || chartData.length === 0) return null;

    let labels;
    let data;

    // Define labels and data based on filter category
    switch (filterCategory) {
      case 'endYear':
        labels = chartData.map(item => item.endYear);
        data = chartData.map(item => item.intensity); // Example: using intensity as data
        break;
      case 'topic':
        labels = chartData.map(item => item.topic);
        data = chartData.map(item => item.intensity); // Example: using intensity as data
        break;
      case 'sector':
        labels = chartData.map(item => item.sector);
        data = chartData.map(item => item.intensity); // Example: using intensity as data
        break;
      case 'region':
        labels = chartData.map(item => item.region);
        data = chartData.map(item => item.intensity); // Example: using intensity as data
        break;
      case 'pestle':
        labels = chartData.map(item => item.pestle);
        data = chartData.map(item => item.intensity); // Example: using intensity as data
        break;
      case 'source':
        labels = chartData.map(item => item.source);
        data = chartData.map(item => item.intensity); // Example: using intensity as data
        break;
      case 'swot':
        labels = chartData.map(item => item.swot);
        data = chartData.map(item => item.intensity); // Example: using intensity as data
        break;
      case 'country':
        labels = chartData.map(item => item.country);
        data = chartData.map(item => item.intensity); // Example: using intensity as data
        break;
      case 'city':
        labels = chartData.map(item => item.city);
        data = chartData.map(item => item.intensity); // Example: using intensity as data
        break;
      default:
        labels = [];
        data = [];
        break;
    }

    // Ensure labels array has unique values
    labels = labels.filter((value, index, self) => self.indexOf(value) === index);

    return (
      <div className="charts-container">
        <div className="chart-card chart-card-big">
          <Bar
            data={{
              labels: labels,
              datasets: [{
                label: `${filterCategory} - Intensity`,
                data: data,
                backgroundColor: 'rgba(75, 192, 192, 0.6)',
              }],
            }}
            options={{
              responsive: true,
              scales: {
                x: {
                  title: {
                    display: true,
                    text: 'Categories' // X-axis title
                  },
                  ticks: {
                    autoSkip: false,
                    maxRotation: 90,
                    minRotation: 90
                  }
                },
                y: { beginAtZero: true },
              },
            }}
          />
        </div>
        <div className="chart-card chart-card-big">
          <Line
            data={{
              labels: labels,
              datasets: [{
                label: `${filterCategory} - Likelihood`,
                data: data,
                borderColor: 'rgba(153, 102, 255, 0.6)',
                fill: false,
              }],
            }}
            options={{
              responsive: true,
              scales: {
                x: {
                  title: {
                    display: true,
                    text: 'Categories' // X-axis title
                  },
                  ticks: {
                    autoSkip: false,
                    maxRotation: 90,
                    minRotation: 90
                  }
                },
                y: { beginAtZero: true },
              },
            }}
          />
        </div>
        <div className="chart-card">
          <Pie
            data={{
              labels: labels.slice(0, 3), // Limiting to first 3 labels for visibility
              datasets: [{
                label: `${filterCategory} - Relevance`,
                data: data.slice(0, 3), // Limiting to first 3 data points for consistency
                backgroundColor: [
                  'rgba(255, 99, 132, 0.6)',
                  'rgba(54, 162, 235, 0.6)',
                  'rgba(255, 206, 86, 0.6)',
                ],
              }],
            }}
            options={{
              responsive: true,
              plugins: {
                legend: {
                  position: 'top', // Adjust legend position as needed
                },
              },
            }}
          />
        </div>
        <div className="chart-card">
          <Scatter
            data={{
              datasets: [{
                label: `${filterCategory} - Intensity vs Likelihood`,
                data: chartData.map(item => ({
                  x: item.intensity,
                  y: item.likelihood,
                })),
                backgroundColor: 'rgba(255, 99, 132, 1)',
              }],
            }}
            options={{
              responsive: true,
              scales: {
                x: { beginAtZero: true },
                y: { beginAtZero: true },
              },
            }}
          />
        </div>
        <div className="chart-card">
          <Bubble
            data={{
              datasets: [{
                label: `${filterCategory} - Intensity vs Likelihood vs Relevance`,
                data: chartData.map(item => ({
                  x: item.intensity,
                  y: item.likelihood,
                  r: item.relevance,
                })),
                backgroundColor: 'rgba(75, 192, 192, 0.6)',
              }],
            }}
            options={{
              responsive: true,
              scales: {
                x: { beginAtZero: true },
                y: { beginAtZero: true },
              },
            }}
          />
        </div>
        <div className="chart-card">
          <Doughnut
            data={{
              labels: labels.slice(0, 3), // Limiting to first 3 labels for visibility
              datasets: [{
                label: `${filterCategory} - Doughnut Chart`,
                data: data.slice(0, 3), // Limiting to first 3 data points for consistency
                backgroundColor: [
                  'rgba(255, 99, 132, 0.6)',
                  'rgba(54, 162, 235, 0.6)',
                  'rgba(255, 206, 86, 0.6)',
                ],
              }],
            }}
            options={{
              responsive: true,
              plugins: {
                legend: {
                  position: 'top', // Adjust legend position as needed
                },
              },
            }}
          />
        </div>
      </div>
    );
  };

  return (
    <div className="vis-dash">
      <header>
        <h1 className="visdash-logo">VisDash</h1>
      </header>
      <div className="filters">
        <label>Filter by:</label>
        <select value={filterCategory} onChange={handleCategoryChange}>
          <option value="endYear">End Year</option>
          <option value="topic">Topic</option>
          <option value="sector">Sector</option>
          <option value="region">Region</option>
          <option value="pestle">PEST</option>
          <option value="source">Source</option>
          <option value="swot">SWOT</option>
          <option value="country">Country</option>
          <option value="city">City</option>
        </select>
        <label>Enter {filterCategory}:</label>
        <input type="text" value={filterValue} onChange={handleValueChange} />
      </div>
      <div className="content">
        {loading ? (
          <div>Loading...</div>
        ) : error ? (
          <div>Error: {error}</div>
        ) : (
          renderCharts()
        )}
      </div>
      <footer>
  <p className="footer">&copy; 2024 VisDash. All rights reserved.</p>
</footer>

    </div>
  );
};

export default VisDash;
