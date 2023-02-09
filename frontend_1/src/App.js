import React, { useEffect, useState } from "react";
import loader from './loader.svg';
import './App.css';

function App() {
  const [error] = useState(null);
  const [isLoaded, setIsLoaded] = useState(false);
  const [result, setResult] = useState([]);

  useEffect(() => {
    fetch("/api/check")
      .then(res => res.json())
      .then(
        (apiResult) => {
          setIsLoaded(true);
          setResult(apiResult);
        },
        (error) => {
          setIsLoaded(true);
          setResult(error);
        }
      )
  }, [])

  if (error) {
    return <div>Error: {error.message}</div>;
  } else if (!isLoaded) {
    return (
      <div className="App">
        <header className="App-header">
          <img src={loader} className="App-logo" alt="logo" />
          
          <p>
            Waiting for the results...
          </p>
        </header>
      </div>
    );
  } else {
    console.log(result);
    return (
      <div className="App">
        <header className="App-header">
          <p>
            {JSON.stringify(result)}
          </p>
        </header>
      </div>
    );
  }
}

export default App;
