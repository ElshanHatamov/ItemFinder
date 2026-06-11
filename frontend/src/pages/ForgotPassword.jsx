import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import api from '../api/axios';
import { Mail, Loader2, ArrowLeft, KeyRound } from 'lucide-react';

const ForgotPassword = () => {
    const navigate = useNavigate();

    const [email, setEmail] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!email.trim()) {
            setError('Email boş ola bilməz.');
            return;
        }

        setLoading(true);
        setError('');
        setSuccess('');

        try {
            const response = await api.post('/auth/forgot-password', {
                email,
            });

            setSuccess(
                typeof response.data === 'string'
                    ? response.data
                    : 'Şifrə bərpa kodu email ünvanınıza göndərildi.'
            );

            setTimeout(() => {
                navigate('/reset-password', {
                    state: {
                        email,
                    },
                });
            }, 1200);
        } catch (err) {
            setError(
                err.response?.data?.message ||
                'Şifrə bərpa kodu göndərilərkən xəta baş verdi.'
            );
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen flex items-center justify-center px-4 py-12 bg-slate-50 dark:bg-slate-950 relative overflow-hidden">
            <div className="absolute top-[-10%] left-[-10%] w-[40%] h-[40%] bg-primary-500/10 blur-[120px] rounded-full" />
            <div className="absolute bottom-[-10%] right-[-10%] w-[40%] h-[40%] bg-indigo-500/10 blur-[120px] rounded-full" />

            <div className="max-w-md w-full animate-fade-in relative">
                <Link
                    to="/login"
                    className="inline-flex items-center text-slate-500 hover:text-primary-600 mb-8 transition-colors"
                >
                    <ArrowLeft size={20} className="mr-2" />
                    Giriş səhifəsinə qayıt
                </Link>

                <div className="bg-white dark:bg-slate-900 p-8 rounded-[2.5rem] shadow-2xl border border-slate-200 dark:border-slate-800">
                    <div className="text-center mb-8">
                        <div className="w-16 h-16 bg-primary-50 dark:bg-primary-900/30 text-primary-600 dark:text-primary-400 rounded-2xl flex items-center justify-center mx-auto mb-5">
                            <KeyRound size={32} />
                        </div>

                        <h2 className="text-3xl font-bold text-slate-900 dark:text-white mb-2">
                            Şifrəni bərpa et
                        </h2>

                        <p className="text-slate-500 dark:text-slate-400">
                            Email ünvanınızı daxil edin, sizə təsdiq kodu göndərək
                        </p>
                    </div>

                    {error && (
                        <div className="mb-6 p-4 bg-rose-50 dark:bg-rose-900/20 border border-rose-200 dark:border-rose-800 rounded-2xl text-rose-600 dark:text-rose-400 text-sm font-medium">
                            {error}
                        </div>
                    )}

                    {success && (
                        <div className="mb-6 p-4 bg-emerald-50 dark:bg-emerald-900/20 border border-emerald-200 dark:border-emerald-800 rounded-2xl text-emerald-600 dark:text-emerald-400 text-sm font-medium">
                            {success}
                        </div>
                    )}

                    <form onSubmit={handleSubmit} className="space-y-6">
                        <div>
                            <label className="block text-sm font-semibold text-slate-700 dark:text-slate-300 mb-2">
                                Email ünvanı
                            </label>

                            <div className="relative">
                                <div className="absolute inset-y-0 left-4 flex items-center pointer-events-none text-slate-400">
                                    <Mail size={18} />
                                </div>

                                <input
                                    type="email"
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                    placeholder="test@gmail.com"
                                    className="w-full pl-12 pr-4 py-4 bg-slate-50 dark:bg-slate-800 border border-transparent rounded-2xl focus:ring-2 focus:ring-primary-500 focus:bg-white dark:focus:bg-slate-700 outline-none text-slate-900 dark:text-white transition-all"
                                />
                            </div>
                        </div>

                        <button
                            type="submit"
                            disabled={loading}
                            className="w-full py-4 bg-primary-600 hover:bg-primary-700 disabled:bg-primary-400 text-white rounded-2xl font-bold transition-all shadow-xl shadow-primary-500/25 flex items-center justify-center"
                        >
                            {loading ? (
                                <Loader2 size={24} className="animate-spin" />
                            ) : (
                                'Kod göndər'
                            )}
                        </button>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default ForgotPassword;