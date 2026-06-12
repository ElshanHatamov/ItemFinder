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
    Lock,
    KeyRound,
} from 'lucide-react';

const Profile = () => {
    const { logout } = useAuth();
    const navigate = useNavigate();

    const [profile, setProfile] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    const [oldPassword, setOldPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [passwordMessage, setPasswordMessage] = useState('');
    const [passwordError, setPasswordError] = useState('');
    const [changingPassword, setChangingPassword] = useState(false);

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

    const handleChangePassword = async (e) => {
        e.preventDefault();

        setPasswordMessage('');
        setPasswordError('');

        if (!oldPassword.trim()) {
            setPasswordError('Köhnə şifrə boş ola bilməz.');
            return;
        }

        if (!newPassword.trim()) {
            setPasswordError('Yeni şifrə boş ola bilməz.');
            return;
        }

        if (newPassword.length < 8) {
            setPasswordError('Yeni şifrə minimum 8 simvol olmalıdır.');
            return;
        }

        setChangingPassword(true);

        try {
            const token = localStorage.getItem('access_token');

            const response = await api.patch(
                '/auth/change-password',
                {
                    oldPassword,
                    newPassword,
                },
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );

            setPasswordMessage(
                typeof response.data === 'string'
                    ? response.data
                    : 'Şifrə uğurla dəyişdirildi.'
            );

            setOldPassword('');
            setNewPassword('');

            setTimeout(async () => {
                await logout();
                navigate('/login');
            }, 1500);
        } catch (err) {
            console.error('Şifrə dəyişdirilərkən xəta:', err);
            setPasswordError(
                err.response?.data?.message ||
                'Şifrə dəyişdirilərkən xəta baş verdi.'
            );
        } finally {
            setChangingPassword(false);
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

                    <div className="mb-10 p-6 md:p-8 rounded-3xl border border-slate-200 dark:border-slate-800 bg-slate-50 dark:bg-slate-800/60">
                        <div className="flex items-center mb-6">
                            <div className="w-12 h-12 rounded-2xl bg-primary-100 dark:bg-primary-900/30 text-primary-600 dark:text-primary-400 flex items-center justify-center mr-4">
                                <KeyRound size={24} />
                            </div>

                            <div>
                                <h2 className="text-2xl font-bold text-slate-900 dark:text-white">
                                    Şifrəni dəyiş
                                </h2>

                                <p className="text-slate-500 dark:text-slate-400 text-sm">
                                    Hesab təhlükəsizliyi üçün köhnə şifrənizi təsdiqləyin
                                </p>
                            </div>
                        </div>

                        {passwordError && (
                            <div className="mb-5 p-4 bg-rose-50 dark:bg-rose-900/20 border border-rose-200 dark:border-rose-800 rounded-2xl text-rose-600 dark:text-rose-400 text-sm font-medium flex items-center">
                                <AlertCircle size={18} className="mr-2" />
                                {passwordError}
                            </div>
                        )}

                        {passwordMessage && (
                            <div className="mb-5 p-4 bg-emerald-50 dark:bg-emerald-900/20 border border-emerald-200 dark:border-emerald-800 rounded-2xl text-emerald-600 dark:text-emerald-400 text-sm font-medium flex items-center">
                                <CheckCircle size={18} className="mr-2" />
                                {passwordMessage}
                            </div>
                        )}

                        <form onSubmit={handleChangePassword} className="grid md:grid-cols-2 gap-5">
                            <div>
                                <label className="block text-sm font-semibold text-slate-700 dark:text-slate-300 mb-2">
                                    Köhnə şifrə
                                </label>

                                <div className="relative">
                                    <Lock
                                        size={18}
                                        className="absolute left-4 top-4 text-slate-400"
                                    />

                                    <input
                                        type="password"
                                        value={oldPassword}
                                        onChange={(e) => setOldPassword(e.target.value)}
                                        placeholder="Köhnə şifrənizi yazın"
                                        className="w-full pl-12 pr-4 py-4 rounded-2xl bg-white dark:bg-slate-900 border border-transparent focus:ring-2 focus:ring-primary-500 outline-none text-slate-900 dark:text-white"
                                    />
                                </div>
                            </div>

                            <div>
                                <label className="block text-sm font-semibold text-slate-700 dark:text-slate-300 mb-2">
                                    Yeni şifrə
                                </label>

                                <div className="relative">
                                    <Lock
                                        size={18}
                                        className="absolute left-4 top-4 text-slate-400"
                                    />

                                    <input
                                        type="password"
                                        value={newPassword}
                                        onChange={(e) => setNewPassword(e.target.value)}
                                        placeholder="Yeni şifrənizi yazın"
                                        className="w-full pl-12 pr-4 py-4 rounded-2xl bg-white dark:bg-slate-900 border border-transparent focus:ring-2 focus:ring-primary-500 outline-none text-slate-900 dark:text-white"
                                    />
                                </div>
                            </div>

                            <button
                                type="submit"
                                disabled={changingPassword}
                                className="md:col-span-2 w-full py-4 bg-primary-600 hover:bg-primary-700 disabled:bg-primary-400 text-white rounded-2xl font-bold transition-all shadow-xl shadow-primary-500/20 flex items-center justify-center"
                            >
                                {changingPassword ? (
                                    <Loader2 size={22} className="animate-spin" />
                                ) : (
                                    'Şifrəni yenilə'
                                )}
                            </button>
                        </form>
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