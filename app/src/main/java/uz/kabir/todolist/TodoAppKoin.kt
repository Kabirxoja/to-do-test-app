package uz.kabir.todolist

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import uz.kabir.todolist.di.appModule
import uz.kabir.todolist.di.databaseModule

class TodoAppKoin : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@TodoAppKoin)
            modules(
                databaseModule,
                appModule
            )
        }
    }
}