package dev.wickedev.voca.infrastructure.configuration

import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.beans.factory.config.BeanDefinitionCustomizer
import org.springframework.beans.factory.support.RootBeanDefinition
import org.springframework.context.support.GenericApplicationContext
import org.springframework.data.r2dbc.core.R2dbcEntityOperations
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactory
import org.springframework.data.repository.core.support.ReactiveRepositoryFactorySupport
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.fu.kofu.r2dbc.DataR2dbcDsl
import java.lang.reflect.Constructor
import java.util.function.Supplier


fun DataR2dbcDsl.enableR2dbcRepositories() {
    context.registerBean(
        R2dbcRepositoryFactory::class.java,
        Supplier {
            val operations = context.getBean(R2dbcEntityOperations::class.java)
            R2dbcRepositoryFactory(operations)
        }
    )

    registerReactiveRepositoryFactoryTo(context)
}

class RepositoryBeanInfo(
    val beanName: String,
    val beanClass: Class<*>
)

class ClassDerivedBeanDefinition : RootBeanDefinition {
    constructor(beanClass: Class<*>, previousBeanDefinition: BeanDefinition) : super(beanClass) {
        BeanDefinitionCustomizer { bd ->
            bd.scope = previousBeanDefinition.scope
            bd.isLazyInit = previousBeanDefinition.isLazyInit
            bd.isPrimary = previousBeanDefinition.isPrimary
            bd.isAutowireCandidate = previousBeanDefinition.isAutowireCandidate
            bd.initMethodName = previousBeanDefinition.initMethodName
            bd.destroyMethodName = previousBeanDefinition.destroyMethodName
            bd.description = previousBeanDefinition.description
            bd.role = previousBeanDefinition.role
        }.customize(this)
    }

    constructor(original: ClassDerivedBeanDefinition) : super(original)

    override fun getPreferredConstructors(): Array<Constructor<*>>? {
        val clazz = beanClass
        val primaryCtor = BeanUtils.findPrimaryConstructor(clazz)
        if (primaryCtor != null) {
            return arrayOf(primaryCtor)
        }
        val publicCtors = clazz.constructors
        return if (publicCtors.isNotEmpty()) {
            publicCtors
        } else null
    }

    override fun cloneBeanDefinition(): RootBeanDefinition {
        return ClassDerivedBeanDefinition(this)
    }
}

fun registerReactiveRepositoryFactoryTo(context: GenericApplicationContext) {
    context.addBeanFactoryPostProcessor { beanFactory ->
        val repoBeans = mutableListOf<RepositoryBeanInfo>()

        beanFactory.beanNamesIterator.forEach { beanName ->
            if (beanFactory.getType(beanName).interfaces.contains(ReactiveCrudRepository::class.java)) {
                val beanClass = beanFactory.getType(beanName, true)
                beanClass?.let { repoBeans.add(RepositoryBeanInfo(beanName, it)) }
            }
        }

        repoBeans.forEach { info ->
            val beanName = info.beanName
            val beanClass = info.beanClass
            val previousBeanDefinition = context.getBeanDefinition(beanName)
            val beanDefinition = ClassDerivedBeanDefinition(beanClass, previousBeanDefinition).apply {
                instanceSupplier = Supplier {
                    val factory = context.getBean(ReactiveRepositoryFactorySupport::class.java)
                    factory.getRepository(beanClass)
                }
            }

            context.removeBeanDefinition(beanName)
            context.registerBeanDefinition(beanName, beanDefinition)
        }
    }
}
