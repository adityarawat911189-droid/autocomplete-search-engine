import { useState } from 'react';
import './App.css';

function App() {
  const [query, setQuery] = useState('');
  const [suggestions, setSuggestions] = useState([]);
  const userId = 'user1'; // abhi ke liye fixed, baad mein real login se aayega

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

  const handleSuggestionClick = async (word) => {
    setQuery(word);
    setSuggestions([]);

    // Backend ko batao ki yeh word select hua (personalization ke liye)
    try {
      await fetch(`http://localhost:8080/api/select?userId=${userId}&word=${word}`, {
        method: 'POST',
      });
    } catch (error) {
      console.error('Error recording selection:', error);
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
                  <li key={index} onClick={() => handleSuggestionClick(word)}>
                    {word}
                  </li>
              ))}
            </ul>
        )}
      </div>
  );
}

export default App;