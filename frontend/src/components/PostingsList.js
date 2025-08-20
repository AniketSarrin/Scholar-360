import React, { useEffect, useState } from 'react';
import axios from 'axios';
import {
  Box,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Card,
  CardContent,
  CardActions,
  Typography,
  Button,
} from '@mui/material';

const PostingsList = () => {
  const [postings, setPostings] = useState([]);
  const [filterType, setFilterType] = useState('ALL');
  const [filterSchool, setFilterSchool] = useState('ALL');
  const [filterDept, setFilterDept] = useState('ALL');

  const [schools, setSchools] = useState([]);
  const [departments, setDepartments] = useState([]);

  useEffect(() => {
    fetchSchools();
    fetchDepartments();
  }, []);

  useEffect(() => {
    fetchPostings();
  }, [filterType, filterSchool, filterDept]);

  const fetchSchools = async () => {
    try {
      const res = await axios.get('http://localhost:8080/api/postings/schools');
      setSchools(res.data);
    } catch (err) {
      console.error('Error fetching schools:', err);
    }
  };

  const fetchDepartments = async () => {
    try {
      const res = await axios.get('http://localhost:8080/api/postings/departments');
      setDepartments(res.data);
    } catch (err) {
      console.error('Error fetching departments:', err);
    }
  };

  const fetchPostings = async () => {
    try {
      // Build query params dynamically
      let url = 'http://localhost:8080/api/postings/filter?';

      if (filterType !== 'ALL') url += `postingType=${filterType}&`;
      if (filterSchool !== 'ALL') url += `school=${encodeURIComponent(filterSchool)}&`;
      if (filterDept !== 'ALL') url += `department=${encodeURIComponent(filterDept)}&`;

      // Remove trailing &
      url = url.endsWith('&') ? url.slice(0, -1) : url;

      // If no filters, call /api/postings (all)
      if (url.endsWith('?')) url = 'http://localhost:8080/api/postings';

      const response = await axios.get(url);
      setPostings(response.data);
    } catch (error) {
      console.error('Error fetching postings:', error);
    }
  };

  return (
    <Box p={3}>
      <Typography variant="h4" gutterBottom color="primary">
        Scholar360
      </Typography>

      <Box display="flex" gap={2} flexWrap="wrap" mb={3}>
        <FormControl sx={{ minWidth: 150 }}>
          <InputLabel id="filter-type-label">Posting Type</InputLabel>
          <Select
            labelId="filter-type-label"
            value={filterType}
            label="Posting Type"
            onChange={(e) => setFilterType(e.target.value)}
          >
            <MenuItem value="ALL">All</MenuItem>
            <MenuItem value="ADMISSION">Admission</MenuItem>
            <MenuItem value="JOB">Job</MenuItem>
            <MenuItem value="INTERNSHIP">Internship</MenuItem>
            <MenuItem value="RESEARCH">Research</MenuItem>
            <MenuItem value="EXAM">Exam</MenuItem>
          </Select>
        </FormControl>

        <FormControl sx={{ minWidth: 150 }}>
          <InputLabel id="filter-school-label">School</InputLabel>
          <Select
            labelId="filter-school-label"
            value={filterSchool}
            label="School"
            onChange={(e) => setFilterSchool(e.target.value)}
          >
            <MenuItem value="ALL">All</MenuItem>
            {schools.map((school) => (
              <MenuItem key={school} value={school}>
                {school}
              </MenuItem>
            ))}
          </Select>
        </FormControl>

        <FormControl sx={{ minWidth: 150 }}>
          <InputLabel id="filter-dept-label">Department</InputLabel>
          <Select
            labelId="filter-dept-label"
            value={filterDept}
            label="Department"
            onChange={(e) => setFilterDept(e.target.value)}
          >
            <MenuItem value="ALL">All</MenuItem>
            {departments.map((dept) => (
              <MenuItem key={dept} value={dept}>
                {dept}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
      </Box>

      <Box display="grid" gridTemplateColumns="repeat(auto-fill, minmax(280px, 1fr))" gap={2}>
        {postings.map(post => (
            <div 
              key={post.id} 
              style={{ 
                border: '1px solid #ccc', 
                marginBottom: '10px', 
                padding: '10px',
                backgroundColor: '#f9f9f9',
                borderRadius: '5px'
              }}
            >
              <p style={{ fontWeight: 'bold', fontSize: '1.1rem', marginBottom: '8px', color: '#333' }}>
                {post.content}
              </p>
              <h3 style={{ margin: '0 0 5px 0', color: '#555' }}>
                {post.school}
              </h3>
              <p style={{ margin: 0, fontStyle: 'italic', color: '#777' }}>
                {post.department}
              </p>
              <p><strong>Type:</strong> {post.postingType}</p>
              <p><strong>Date:</strong> {post.date}</p>
              <a href={post.link} target="_blank" rel="noopener noreferrer">More Info</a>
            </div>
            ))}

      </Box>
    </Box>
  );
};

export default PostingsList;
