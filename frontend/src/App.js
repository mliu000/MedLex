import React, { useState } from 'react';
// import symptoms from './symptoms.json';
import './App.css';
import steth from './logoicon.png';


const TextBox = ({ loading, setLoading, setResponse }) => {
  const [text, setText] = useState("");

  const handleChange = (event) => {
    setText(event.target.value); 
  };

  const handleKeyDown = async (event) => {
    if (event.key === 'Enter' && text.trim() !== "") {
      setLoading(true);
      setResponse(null);
      const symptomsData = await processInput(text);
      setTimeout(() => {
        setResponse(symptomsData);
        setLoading(false);
      }, 2000);
      // Simulate loading or navigate to another page here
      // e.g., window.location.href = `/newpage?query=${encodeURIComponent(text)}`;
    }
  };


  async function processInput(input) {
    try {
      const response = await fetch(`/api/symptoms/${input}`);
      if (response.ok) {
        const data = await response.json();
        return data; // Return the data containing top 4 symptoms
      } else {
        throw new Error('Failed to fetch symptoms data');
      }
    } catch (error) {
      console.error('Error fetching symptoms data:', error);
    }
  }

  if (loading) return null; // Do not render if loading is true

  return (
    <div className="TextBox">
      <input
      type="text"
      id="textBox"
      value={text}
      onChange={handleChange}
      onKeyDown={handleKeyDown}
      className="input-field"
      placeholder='e.g. "I feel dizzy and my chest feels tight"'
      autoFocus
      />
    </div>
  );
};

const Header = ({ loading }) => {
  if (loading) return null; // Do not render if loading is true
  return (
    <div className="App-header">
      <div className="header-content">
        <h1>MedLex</h1>
        <img src={steth} alt="steth" className="steth" />
      </div>
      <h2 className="subheader">A symptom interpreter.</h2>
    </div>
  );
};

const Results = ({ response }) => {
  if (!response) return null; // Do not render if loading is true
  return (
    <div className="App-header">
      <h2>Your top potential symptoms</h2>
      <ul>
        {response.map((pair, index) => 
          <li key={index}>
            <strong>{pair.first}</strong>: {pair.second}
          </li>)}
      </ul>
    </div>
  );
};

function App() {
  const [loading, setLoading] = useState(false);
  const [response, setResponse] = useState(false);

  const handleInfoRecieved = (event) => {
    if (false) { //insert some variable from backend
      setLoading(false);
      setResponse(true);
    }
  };

  return (
    <div className="App">
      
      {response ? (
        <>
          <Results response = {response}/>
          <TextBox loading = {loading} setLoading={setLoading} setResponse={setResponse} />
        </>
      ) : (
        (loading ? (
          <p className="loading-text">Please give us a moment to analyze</p>
        ) : (
          <>
          <Header loading={loading} />
          <TextBox loading={loading} setLoading={setLoading} setResponse={setResponse} />
          </>
        ))
      )}

    </div>
  );
}

export default App;