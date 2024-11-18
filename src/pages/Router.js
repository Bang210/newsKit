import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Footer from '../components/layouts/Footer';
import Home from "../pages/Home";

function Router() {
    return (
        <div className='flex flex-col min-h-screen'>
            <BrowserRouter>
                <Routes>
                    <Route path="" element={<Home />} />
                </Routes>
                <Footer />
            </BrowserRouter>
        </div>
    )
}

export default Router;