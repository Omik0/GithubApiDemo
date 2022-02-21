package ge.example.githubapidemo.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ge.example.githubapidemo.BuildConfig
import ge.example.githubapidemo.utils.ConnectivityListener
import ge.example.githubapidemo.utils.Keys
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder
            .addInterceptor(
                Interceptor { chain ->
                    val requestBuilder = chain.request().newBuilder()
                    requestBuilder.header("Authorization", Keys.githubToken())
                    return@Interceptor chain.proceed(requestBuilder.build())
                }
            )
            .addInterceptor(loggingInterceptor)
            .dispatcher(Dispatcher().apply { maxRequests = 5 })
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideMoshiO(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(BuildConfig.GITHUB_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
    }

    @Singleton
    @Provides
    fun provideConnectivityManager(
        @ApplicationContext context: Context,
    ): ConnectivityListener {
        return ConnectivityListener(context)
    }

}