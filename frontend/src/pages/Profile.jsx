import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import api from '../api/axios';
import { formatDate } from '../utils/formatDate';
import {
    User,
    Mail,
    Phone,
    ShieldCheck,
    LogOut,
    Package,
    PlusCircle,
    ArrowLeft,
    CheckCircle,
    Loader2,
    Calendar,
    AlertCircle,
} from 'lucide-react';

const Profile = () => {
    const { logout } = useAuth();
    const navigate = useNavigate();

    const [profile, setProfile] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    useEffect(() => {
        fetchProfile();
    }, []);

    const fetchProfile = async () => {
        setLoading(true);
        setError('');

        try {
            const token = localStorage.getItem('access_token');

            const response = await api.get('/auth/profile', {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            setProfile(response.data);
        } catch (err) {
            console.error('Profil məlumatları yüklənərkən xəta:', err);
            setError(
                err.response?.data?.message ||
                'Profil məlumatları yüklənərkən xəta baş verdi.'
            );
        } finally {
            setLoading(false);
        }
    };

    const handleLogout = async () => {
        await logout();
        navigate('/login');
    };

    if (loading) {
        return (
            <div className="min-h-screen pt-20 flex justify-center">
                <Loader2 size={48} className="animate-spin text-primary-600" />
            </div>
        );
    }

    return (
        <div className="min-h-screen px-4 py-12">
            <div className="max-w-5xl mx-auto">
                <Link
                    to="/"
                    className="inline-flex items-center text-slate-500 hover:text-primary-600 mb-8 transition-colors"
                >
                    <ArrowLeft size={20} className="mr-2" />
                    Ana səhifəyə qayıt
                </Link>

                <div className="bg-white dark:bg-slate-900 rounded-3xl border border-slate-200 dark:border-slate-800 shadow-xl p-8 md:p-12">
                    {error && (
                        <div className="mb-8 p-4 bg-rose-50 dark:bg-rose-900/20 border border-rose-200 dark:border-rose-800 rounded-2xl text-rose-600 dark:text-rose-400 text-sm font-medium flex items-center">
                            <AlertCircle size={18} className="mr-2" />
                            {error}
                        </div>
                    )}

                    <div className="flex flex-col md:flex-row items-center gap-8 mb-10">
                        <div className="w-32 h-32 rounded-3xl bg-gradient-to-br from-primary-600 to-indigo-600 flex items-center justify-center text-white shadow-xl">
                            <User size={60} />
                        </div>

                        <div>
                            <h1 className="text-4xl font-bold text-slate-900 dark:text-white">
                                {profile?.name} {profile?.surname}
                            </h1>

                            <p className="text-slate-500 dark:text-slate-400 mt-2">
                                İstifadəçi məlumatları və sürətli keçidlər
                            </p>
                        </div>
                    </div>

                    <div className="grid md:grid-cols-2 gap-6 mb-10">
                        <div className="p-6 rounded-3xl bg-slate-50 dark:bg-slate-800">
                            <div className="flex items-center mb-3">
                                <User size={22} className="text-primary-600 mr-3" />
                                <span className="font-semibold">Ad Soyad</span>
                            </div>

                            <p className="text-slate-700 dark:text-slate-300">
                                {profile?.name} {profile?.surname}
                            </p>
                        </div>

                        <div className="p-6 rounded-3xl bg-slate-50 dark:bg-slate-800">
                            <div className="flex items-center mb-3">
                                <Mail size={22} className="text-primary-600 mr-3" />
                                <span className="font-semibold">Email ünvanı</span>
                            </div>

                            <p className="text-slate-700 dark:text-slate-300 break-all">
                                {profile?.email || 'Məlumat tapılmadı'}
                            </p>
                        </div>

                        <div className="p-6 rounded-3xl bg-slate-50 dark:bg-slate-800">
                            <div className="flex items-center mb-3">
                                <Phone size={22} className="text-primary-600 mr-3" />
                                <span className="font-semibold">Telefon nömrəsi</span>
                            </div>

                            <p className="text-slate-700 dark:text-slate-300">
                                {profile?.phone || 'Məlumat tapılmadı'}
                            </p>
                        </div>

                        <div className="p-6 rounded-3xl bg-slate-50 dark:bg-slate-800">
                            <div className="flex items-center mb-3">
                                <Calendar size={22} className="text-primary-600 mr-3" />
                                <span className="font-semibold">Qeydiyyat tarixi</span>
                            </div>

                            <p className="text-slate-700 dark:text-slate-300">
                                {profile?.createAt ? formatDate(profile.createAt) : 'Məlumat tapılmadı'}
                            </p>
                        </div>

                        <div className="p-6 rounded-3xl bg-slate-50 dark:bg-slate-800 md:col-span-2">
                            <div className="flex items-center mb-3">
                                <ShieldCheck size={22} className="text-emerald-600 mr-3" />
                                <span className="font-semibold">Hesab statusu</span>
                            </div>

                            <div className="flex items-center text-emerald-600 font-bold">
                                <CheckCircle size={18} className="mr-2" />
                                Aktiv
                            </div>
                        </div>
                    </div>

                    <div className="grid md:grid-cols-3 gap-5">
                        <Link
                            to="/my-items"
                            className="p-6 rounded-3xl border border-slate-200 dark:border-slate-700 hover:shadow-lg transition-all hover:-translate-y-1"
                        >
                            <Package size={32} className="text-primary-600 mb-4" />

                            <h3 className="font-bold text-lg mb-2">
                                Elanlarım
                            </h3>

                            <p className="text-slate-500 text-sm">
                                Bütün elanlarınızı idarə edin
                            </p>
                        </Link>

                        <Link
                            to="/create-item"
                            className="p-6 rounded-3xl border border-slate-200 dark:border-slate-700 hover:shadow-lg transition-all hover:-translate-y-1"
                        >
                            <PlusCircle size={32} className="text-primary-600 mb-4" />

                            <h3 className="font-bold text-lg mb-2">
                                Yeni elan
                            </h3>

                            <p className="text-slate-500 text-sm">
                                Yeni itmiş və ya tapılmış əşya əlavə edin
                            </p>
                        </Link>

                        <button
                            onClick={handleLogout}
                            className="text-left p-6 rounded-3xl border border-red-200 dark:border-red-800 bg-red-50 dark:bg-red-900/20 hover:shadow-lg transition-all hover:-translate-y-1"
                        >
                            <LogOut size={32} className="text-red-600 mb-4" />

                            <h3 className="font-bold text-lg text-red-600 mb-2">
                                Çıxış
                            </h3>

                            <p className="text-red-500 text-sm">
                                Hesabdan təhlükəsiz çıxış et
                            </p>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Profile;