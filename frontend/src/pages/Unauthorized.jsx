import React from 'react';
import { Link } from 'react-router-dom';
import { ShieldX } from 'lucide-react';

const Unauthorized = () => {
    return (
        <div className="min-h-[80vh] flex items-center justify-center">
            <div className="text-center">

                <ShieldX
                    size={80}
                    className="mx-auto text-red-500 mb-4"
                />

                <h1 className="text-5xl font-bold mb-4">
                    403
                </h1>

                <h2 className="text-2xl font-semibold mb-4">
                    İcazəniz yoxdur
                </h2>

                <p className="text-slate-500 mb-6">
                    Bu səhifəyə daxil olmaq üçün administrator hüquqları tələb olunur.
                </p>

                <Link
                    to="/"
                    className="px-6 py-3 bg-primary-600 text-white rounded-xl"
                >
                    Ana səhifəyə qayıt
                </Link>

            </div>
        </div>
    );
};

export default Unauthorized;