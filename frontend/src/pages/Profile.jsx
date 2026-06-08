import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import {
    User,
    Mail,
    ShieldCheck,
    LogOut,
    Package,
    PlusCircle,
    ArrowLeft,
} from 'lucide-react';

const Profile = () => {
    const { user, logout } = useAuth();
    const navigate = useNavigate();

    const handleLogout = async () => {
        await logout();
        navigate('/login');
    };

    return (
        <div className="min-h-screen px-4 py-12">
            <div className="max-w-5xl mx-auto">
                <Link
                    to="/"
                    className="inline-flex items-center text-slate-500 hover:text-primary-600 mb-8 transition-colors"
                >
                    <ArrowLeft size={20} className="mr-2" />
                    Back to Home
                </Link>

                <div className="bg-white dark:bg-slate-900 rounded-3xl border border-slate-200 dark:border-slate-800 shadow-xl p-8 md:p-12">

                    <div className="flex flex-col md:flex-row items-center gap-8 mb-10">
                        <div className="w-32 h-32 rounded-3xl bg-gradient-to-br from-primary-600 to-indigo-600 flex items-center justify-center text-white">
                            <User size={60} />
                        </div>

                        <div>
                            <h1 className="text-4xl font-bold text-slate-900 dark:text-white">
                                My Profile
                            </h1>

                            <p className="text-slate-500 dark:text-slate-400 mt-2">
                                Account information and quick actions
                            </p>
                        </div>
                    </div>

                    <div className="grid md:grid-cols-2 gap-6 mb-10">

                        <div className="p-6 rounded-3xl bg-slate-50 dark:bg-slate-800">
                            <div className="flex items-center mb-3">
                                <Mail size={22} className="text-primary-600 mr-3" />
                                <span className="font-semibold">Email</span>
                            </div>

                            <p className="text-slate-700 dark:text-slate-300 break-all">
                                {user?.email || 'No email found'}
                            </p>
                        </div>

                        <div className="p-6 rounded-3xl bg-slate-50 dark:bg-slate-800">
                            <div className="flex items-center mb-3">
                                <ShieldCheck size={22} className="text-emerald-600 mr-3" />
                                <span className="font-semibold">Status</span>
                            </div>

                            <p className="text-emerald-600 font-bold">
                                Logged In
                            </p>
                        </div>

                    </div>

                    <div className="grid md:grid-cols-3 gap-5">

                        <Link
                            to="/my-items"
                            className="p-6 rounded-3xl border border-slate-200 dark:border-slate-700 hover:shadow-lg transition-all"
                        >
                            <Package size={32} className="text-primary-600 mb-4" />

                            <h3 className="font-bold text-lg mb-2">
                                My Items
                            </h3>

                            <p className="text-slate-500 text-sm">
                                View your reports
                            </p>
                        </Link>

                        <Link
                            to="/create-item"
                            className="p-6 rounded-3xl border border-slate-200 dark:border-slate-700 hover:shadow-lg transition-all"
                        >
                            <PlusCircle size={32} className="text-primary-600 mb-4" />

                            <h3 className="font-bold text-lg mb-2">
                                Report Item
                            </h3>

                            <p className="text-slate-500 text-sm">
                                Create a new report
                            </p>
                        </Link>

                        <button
                            onClick={handleLogout}
                            className="text-left p-6 rounded-3xl border border-red-200 dark:border-red-800 bg-red-50 dark:bg-red-900/20 hover:shadow-lg transition-all"
                        >
                            <LogOut size={32} className="text-red-600 mb-4" />

                            <h3 className="font-bold text-lg text-red-600 mb-2">
                                Logout
                            </h3>

                            <p className="text-red-500 text-sm">
                                Sign out from your account
                            </p>
                        </button>

                    </div>

                </div>
            </div>
        </div>
    );
};

export default Profile;