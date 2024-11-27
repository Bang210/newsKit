import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPen } from '@fortawesome/free-solid-svg-icons';
import { faNewspaper } from '@fortawesome/free-solid-svg-icons';
import { faRightLong } from '@fortawesome/free-solid-svg-icons';

const Header = () => {
    return (
        <div className="navbar bg-base-300">
            <div className="navbar-start"></div>
            <div className="navbar-center">
                <a href="" role="button" className="btn btn-ghost btn-wide text-3xl font-bold flex justify-center">News Kit<FontAwesomeIcon icon={faPen} /><FontAwesomeIcon icon={faNewspaper} /></a>
            </div>
            <div className="navbar-end"></div>
        </div>
    );
};

export default Header;