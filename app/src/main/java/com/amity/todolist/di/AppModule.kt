package com.amity.todolist.di

import android.app.Application
import androidx.room.Room
import com.amity.todolist.data.local.TodoAppDatabase
import com.amity.todolist.data.local.TodoDAO
import com.amity.todolist.data.remote.ApiConfig
import com.amity.todolist.data.remote.ApiService
import com.amity.todolist.data.repository.RepositoryImpl
import com.amity.todolist.data.repository.datasource.LocalDataSource
import com.amity.todolist.data.repository.datasource.RemoteDataSource
import com.amity.todolist.data.repository.datasourseimpl.LocalDataSourceImpl
import com.amity.todolist.data.repository.datasourseimpl.RemoteDataSourceImpl
import com.amity.todolist.domain.repository.Repository
import com.amity.todolist.utils.ConnectivityObserver
import com.amity.todolist.utils.NetworkConnectivityObserver
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        return  HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor {
            val requestBuilder = it.request().newBuilder()
            requestBuilder.header("Content-Type", "application/json")
            it.proceed(requestBuilder.build())
        }.addInterceptor(httpLoggingInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideMyApi( okHttpClient: OkHttpClient) : ApiService{
        return  Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideGetTodoDAO(todoAppDatabase: TodoAppDatabase):TodoDAO{
        return todoAppDatabase.getTodoDAO()
    }

    @Singleton
    @Provides
    fun provideDatabase(app: Application): TodoAppDatabase {
        return Room.databaseBuilder(app, TodoAppDatabase::class.java, "news_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource{
        return RemoteDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(todoDAO: TodoDAO) : LocalDataSource{
        return LocalDataSourceImpl(todoDAO)
    }

    @Singleton
    @Provides
    fun provideConnectivityObserver(app: Application): ConnectivityObserver {
        return NetworkConnectivityObserver(app)
    }

    @Singleton
    @Provides
    fun provideRepository( remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource, context : Application, connectivityObserver: ConnectivityObserver ) : Repository{
        return RepositoryImpl(remoteDataSource, localDataSource, context, connectivityObserver)
    }

}