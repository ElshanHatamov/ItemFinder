import React, { useState } from 'react';
import { useLocation, useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { Mail, KeyRound, Loader2, CheckCircle, ArrowLeft } from 'lucide-react';

const VerifyEmail = () => {
    const { verifyEmail } = useAuth();

    const navigate = useNavigate();
    const location = useLocation();

    const [email, setEmail] = useState(location.state?.email || '');
    const [code, setCode] = useState('');

    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();

        setError('');
        setSuccess('');
        setLoading(true);

        try {
            const response = await verifyEmail(email, code);

            setSuccess(
                typeof response === 'string'
                    ? response
                    : 'Email uğurla təsdiqləndi'
            );

            setTimeout(() => {
                navigate('/login');
            }, 1500);
        } catch (err) {
            setError(
                err.response?.data?.message ||
                'Təsdiq zamanı xəta baş verdi.'
            );
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen flex items-center justify-center px-4 py-12 bg-slate-50 dark:bg-slate-950">
            <div className="max-w-md w-full">
                <Link
                    to="/register"
                    className="inline-flex items-center text-slate-500 hover:text-primary-600 mb-8 transition-colors"
                >
                    <ArrowLeft size={20} className="mr-2" />
                    Qeydiyyata qayıt
                </Link>

                <div className="bg-white dark:bg-slate-900 p-8 rounded-[2.5rem] shadow-2xl border border-slate-200 dark:border-slate-800">
                    <div className="text-center mb-8">
                        <div className="flex justify-center mb-4">
                            <div className="w-16 h-16 rounded-full bg-primary-100 dark:bg-primary-900/30 flex items-center justify-center">
                                <Mail
                                    size={30}
                                    className="text-primary-600"
                                />
                            </div>
                        </div>

                        <h2 className="text-3xl font-bold text-slate-900 dark:text-white mb-2">
                            Email təsdiqi
                        </h2>

                        <p className="text-slate-500 dark:text-slate-400">
                            Email ünvanınıza göndərilən kodu daxil edin
                        </p>
                    </div>

                    {error && (
                        <div className="mb-5 p-4 bg-rose-50 dark:bg-rose-900/20 border border-rose-200 dark:border-rose-800 rounded-2xl text-rose-600 dark:text-rose-400 text-sm">
                            {error}
                        </div>
                    )}

                    {success && (
                        <div className="mb-5 p-4 bg-emerald-50 dark:bg-emerald-900/20 border border-emerald-200 dark:border-emerald-800 rounded-2xl text-emerald-600 dark:text-emerald-400 text-sm flex items-center">
                            <CheckCircle size={18} className="mr-2" />
                            {success}
                        </div>
                    )}

                    <form
                        onSubmit={handleSubmit}
                        className="space-y-5"
                    >
                        <div>
                            <label className="block text-sm font-semibold text-slate-700 dark:text-slate-300 mb-2">
                                Email ünvanı
                            </label>

                            <input
                                type="email"
                                value={email}
                                onChange={(e) =>
                                    setEmail(e.target.value)
                                }
                                className="w-full px-4 py-4 bg-slate-50 dark:bg-slate-800 rounded-2xl outline-none focus:ring-2 focus:ring-primary-500"
                            />
                        </div>

                        <div>
                            <label className="block text-sm font-semibold text-slate-700 dark:text-slate-300 mb-2">
                                Təsdiq kodu
                            </label>

                            <div className="relative">
                                <div className="absolute inset-y-0 left-4 flex items-center text-slate-400">
                                    <KeyRound size={18} />
                                </div>

                                <input
                                    value={code}
                                    onChange={(e) =>
                                        setCode(e.target.value)
                                    }
                                    placeholder="123456"
                                    className="w-full pl-12 pr-4 py-4 bg-slate-50 dark:bg-slate-800 rounded-2xl outline-none focus:ring-2 focus:ring-primary-500"
                                />
                            </div>
                        </div>

                        <button
                            type="submit"
                            disabled={loading}
                            className="w-full py-4 bg-primary-600 hover:bg-primary-700 disabled:bg-primary-400 text-white rounded-2xl font-bold transition-all flex items-center justify-center"
                        >
                            {loading ? (
                                <Loader2
                                    size={22}
                                    className="animate-spin"
                                />
                            ) : (
                                'Emaili təsdiqlə'
                            )}
                        </button>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default VerifyEmail;