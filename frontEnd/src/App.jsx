import React from 'react'
import Products from "./Components/Products/Products.jsx";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Navbar from "./Components/Home/Navbar.jsx";
import {Toaster} from "react-hot-toast";
import Home from "./Components/Home/Home.jsx";

function App() {

  return (
      <>
          <React.Fragment>
              <Router>
                  <Navbar />
                  <Routes>
                      <Route path="/" element={<Home/>} />
                      <Route path="/products" element={<Products/>} />
                  </Routes>
              </Router>
              <Toaster position = "bottom-center" />
          </React.Fragment>

      </>
  )
}

export default App
