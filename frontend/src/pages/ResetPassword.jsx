import React, { useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import api from '../api/axios';
import {
    ArrowLeft,
    Mail,
    Lock,
    ShieldCheck,
    Loader2,
} from 'lucide-react';

const ResetPassword = () => {
    const navigate = useNavigate();
    const location = useLocation();

    const [email, setEmail] = useState(location.state?.email || '');
    const [code, setCode] = useState('');
    const [newPassword, setNewPassword] = useState('');

    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();

        setError('');
        setSuccess('');
        setLoading(true);

        try {
            const response = await api.post('/auth/reset-password', {
                email,
                code,
                newPassword,
            });

            setSuccess(
                typeof response.data === 'string'
                    ? response.data
                    : 'Şifrə uğurla yeniləndi.'
            );

            setTimeout(() => {
                navigate('/login');
            }, 1500);
        } catch (err) {
            setError(
                err.response?.data?.message ||
                'Şifrə yenilənərkən xəta baş verdi.'
            );
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen flex items-center justify-center px-4 py-12 bg-slate-50 dark:bg-slate-950">
            <div className="max-w-md w-full">
                <Link
                    to="/login"
                    className="inline-flex items-center text-slate-500 hover:text-primary-600 mb-8"
                >
                    <ArrowLeft size={20} className="mr-2" />
                    Giriş səhifəsinə qayıt
                </Link>

                <div className="bg-white dark:bg-slate-900 p-8 rounded-[2.5rem] shadow-2xl border border-slate-200 dark:border-slate-800">
                    <div className="text-center mb-8">
                        <div className="w-16 h-16 bg-primary-50 dark:bg-primary-900/30 rounded-2xl flex items-center justify-center mx-auto mb-4">
                            <ShieldCheck size={30} />
                        </div>

                        <h2 className="text-3xl font-bold mb-2">
                            Yeni şifrə təyin et
                        </h2>

                        <p className="text-slate-500 dark:text-slate-400">
                            Emailə göndərilən kodu daxil edin
                        </p>
                    </div>

                    {error && (
                        <div className="mb-5 p-4 rounded-2xl bg-rose-50 text-rose-600">
                            {error}
                        </div>
                    )}

                    {success && (
                        <div className="mb-5 p-4 rounded-2xl bg-emerald-50 text-emerald-600">
                            {success}
                        </div>
                    )}

                    <form onSubmit={handleSubmit} className="space-y-5">
                        <div>
                            <label className="block mb-2 font-semibold">
                                Email
                            </label>

                            <div className="relative">
                                <Mail
                                    size={18}
                                    className="absolute left-4 top-4 text-slate-400"
                                />

                                <input
                                    type="email"
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                    className="w-full pl-12 pr-4 py-4 rounded-2xl bg-slate-50 dark:bg-slate-800"
                                />
                            </div>
                        </div>

                        <div>
                            <label className="block mb-2 font-semibold">
                                Təsdiq kodu
                            </label>

                            <input
                                type="text"
                                value={code}
                                onChange={(e) => setCode(e.target.value)}
                                placeholder="123456"
                                className="w-full px-4 py-4 rounded-2xl bg-slate-50 dark:bg-slate-800"
                            />
                        </div>

                        <div>
                            <label className="block mb-2 font-semibold">
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
                                    className="w-full pl-12 pr-4 py-4 rounded-2xl bg-slate-50 dark:bg-slate-800"
                                />
                            </div>
                        </div>

                        <button
                            type="submit"
                            disabled={loading}
                            className="w-full py-4 bg-primary-600 hover:bg-primary-700 text-white rounded-2xl font-bold"
                        >
                            {loading ? (
                                <Loader2 size={22} className="animate-spin mx-auto" />
                            ) : (
                                'Şifrəni yenilə'
                            )}
                        </button>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default ResetPassword;