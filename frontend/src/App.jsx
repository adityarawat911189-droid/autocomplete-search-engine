import { useState } from 'react';
import './App.css';

function App() {
  const [query, setQuery] = useState('');
  const [suggestions, setSuggestions] = useState([]);

  const handleInputChange = async (e) => {
    const value = e.target.value;
    setQuery(value);

    if (value.trim() === '') {
      setSuggestions([]);
      return;
    }

    try {
      const response = await fetch(`http://localhost:8080/api/suggest?q=${value}`);
      const data = await response.json();
      setSuggestions(data);
    } catch (error) {
      console.error('Error fetching suggestions:', error);
    }
  };

  return (
      <div className="app-container">
        <h1>Smart Search Autocomplete</h1>
        <input
            type="text"
            value={query}
            onChange={handleInputChange}
            placeholder="Type to search..."
            className="search-box"
        />
        {suggestions.length > 0 && (
            <ul className="suggestions-list">
              {suggestions.map((word, index) => (
                  <li key={index}>{word}</li>
              ))}
            </ul>
        )}
      </div>
  );
}

export default App;