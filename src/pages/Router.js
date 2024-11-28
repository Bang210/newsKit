import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Footer from '../components/layouts/Footer';
import Header from '../components/layouts/Header';
import Home from "./Home";

function Router() {
    return (
        <div className='min-h-screen'>
            <BrowserRouter>
                <Header />
                <Routes>
                    <Route path="" element={<Home />} />
                </Routes>
                <Footer />
            </BrowserRouter>
        </div>
    )
}

export default Router;