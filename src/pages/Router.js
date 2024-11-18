import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Footer from '../components/layouts/Footer';

function Router() {
    return (
        <div className='flex flex-col min-h-screen'>
            <BrowserRouter>
                <Footer />
            </BrowserRouter>
        </div>
    )
}

export default Router;